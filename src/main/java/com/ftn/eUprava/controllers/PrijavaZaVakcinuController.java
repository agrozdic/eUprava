package com.ftn.eUprava.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ftn.eUprava.models.DozaVakcine;
import com.ftn.eUprava.models.Korisnik;
import com.ftn.eUprava.models.PrijavaZaVakcinu;
import com.ftn.eUprava.models.Vakcina;
import com.ftn.eUprava.services.KorisnikService;
import com.ftn.eUprava.services.PrijavaZaVakcinuService;
import com.ftn.eUprava.services.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping(value="/prijaveZaVakcinu")
public class PrijavaZaVakcinuController implements ServletContextAware {

    @Autowired
    private ServletContext servletContext;
    private  String bURL;

    @Autowired
    private PrijavaZaVakcinuService prijavaZaVakcinuService;

    @Autowired
    private VakcinaService vakcinaService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PostConstruct
    public void init() {
        bURL = servletContext.getContextPath() + "/";
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @GetMapping
    public ModelAndView Index(
            @RequestParam(required=false) String ime,
            @RequestParam(required=false) String prezime,
            @RequestParam(required=false) String jmbg,
            HttpSession session) throws IOException {

        List<PrijavaZaVakcinu> prijaveZaVakcinu = prijavaZaVakcinuService.find(ime, prezime, jmbg);
        List<Korisnik> korisnici = korisnikService.findAll();

        ModelAndView rezultat = new ModelAndView("prijaveZaVakcinu");
        rezultat.addObject("prijaveZaVakcinu", prijaveZaVakcinu);
        rezultat.addObject("korisnici", korisnici);
        return rezultat;

    }

    @GetMapping(value="/add")
    public String create(HttpSession session, HttpServletResponse response ) {

        List<Korisnik> korisnici = korisnikService.findAll();
        session.setAttribute("korisnici", korisnici);

        List<Vakcina> vakcine = vakcinaService.findAll();
        session.setAttribute("vakcine", vakcine);

        return "dodavanjePrijaveZaVakcinu";
    }

    @SuppressWarnings("unused")
    @PostMapping(value="/add")
    public void create
            (@RequestParam(value = "korisnikID") @PathVariable("id") Integer korisnikID,
             @RequestParam(value = "vakcinaID") @PathVariable("id") Integer vakcinaID,
             @RequestParam DozaVakcine doza,

             HttpServletResponse response) throws IOException {

        Korisnik korisnik = korisnikService.findOneById(korisnikID);
        Vakcina vakcina = vakcinaService.findOne(vakcinaID);

        PrijavaZaVakcinu prijavaZaVakcinu = new PrijavaZaVakcinu(korisnik, vakcina, doza);
        PrijavaZaVakcinu saved = prijavaZaVakcinuService.save(prijavaZaVakcinu);
        response.sendRedirect(bURL+"prijaveZaVakcinu");
    }


    @SuppressWarnings("unused")
    @PostMapping(value="/delete")
    public void delete(@RequestParam Integer vakcinaID, HttpServletResponse response) throws IOException {
        PrijavaZaVakcinu deleted = prijavaZaVakcinuService.delete(vakcinaID);
        response.sendRedirect(bURL+"prijaveZaVakcinu");
    }
    
    @GetMapping(value="/details")
    @ResponseBody
    public ModelAndView details(@RequestParam Integer vakcinaID) {
        PrijavaZaVakcinu prijavaZaVakcinu  = prijavaZaVakcinuService.findOne(vakcinaID);
        List<Korisnik> korisnici = korisnikService.findAll();
        List<Vakcina> vakcine = vakcinaService.findAll();
        ModelAndView rezultat = new ModelAndView("prijavaZaVakcinu");
        rezultat.addObject("prijavaZaVakcinu", prijavaZaVakcinu);
        rezultat.addObject("korisnici", korisnici);
        rezultat.addObject("vakcine", vakcine);
        return rezultat;
    }

    @PostMapping(value = "/search")
    public ModelAndView search(@RequestParam(required=false) String ime,
                               @RequestParam(required=false) String prezime,
                               @RequestParam(required=false) String jmbg) {

        ModelAndView rezultat = new ModelAndView("prijaveZaVakcinu");
        List<Korisnik> korisnici = korisnikService.findAll();
        List<PrijavaZaVakcinu> prijaveFilter = prijavaZaVakcinuService.find(ime, prezime, jmbg);

        rezultat.addObject("prijaveZaVakcinu", prijaveFilter);
        rezultat.addObject("korisnici", korisnici);

        return rezultat;
    }


    @PostMapping(value = "/dajVakcinu")
    public String dajVakcinu(@RequestParam("korisnikID") Long korisnikID,
                             @RequestParam("vakcinaID") Long vakcinaID,
                             @RequestParam("doza") DozaVakcine doza,
                             RedirectAttributes redirectAttributes) {

        if (doza == DozaVakcine.PRVA) {
            int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Vakcinacija WHERE KorisnikID = ? "
                            + "AND DozaVakcine = 'PRVA'",
                    new Object[]{korisnikID}, Integer.class);

            if (count > 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "First dose has already been taken!");
                return "redirect:/prijaveZaVakcinu";
            }
        }


        if (doza == DozaVakcine.DRUGA) {
            int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Vakcinacija WHERE KorisnikID = ?"
                            + " AND (DozaVakcine = 'PRVA' OR DozaVakcine = 'DRUGA')",
                    new Object[]{korisnikID}, Integer.class);

            if (count == 2) {
                redirectAttributes.addFlashAttribute("errorMessage", "Second dose has already been taken!");
                return "redirect:/prijaveZaVakcinu";
            }

            if (count == 1) {
                LocalDateTime dvVakcinacije = jdbcTemplate.queryForObject("SELECT DatumVreme " +
                                "FROM Vakcinacija WHERE KorisnikID = ? AND DozaVakcine = 'PRVA'",
                        new Object[]{korisnikID}, LocalDateTime.class);

                LocalDateTime nowLDT = LocalDateTime.now();

                long differenceInMinutes = ChronoUnit.MINUTES.between(dvVakcinacije, nowLDT);

                if (differenceInMinutes <= 3) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Time between doses hasn't passed (3 months)!");
                    return "redirect:/prijaveZaVakcinu";
                }
            }

            if (count == 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "First dose hasn't been taken!");
                return "redirect:/prijaveZaVakcinu";
            }
        }

        if (doza == DozaVakcine.TRECA) {
            int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Vakcinacija WHERE "
                            + "KorisnikID = ? AND (DozaVakcine = 'PRVA' OR DozaVakcine = 'DRUGA' OR DozaVakcine = 'TRECA')",
                    new Object[]{korisnikID}, Integer.class);

            if (count == 3) {
                redirectAttributes.addFlashAttribute("errorMessage", "Third dose has already been taken!");
                return "redirect:/prijaveZaVakcinu";
            }

            if (count == 2) {
                LocalDateTime dvVakcinacije = jdbcTemplate.queryForObject("SELECT DatumVreme"
                                + " FROM Vakcinacija WHERE KorisnikID = ? AND DozaVakcine = 'DRUGA'",
                        new Object[]{korisnikID}, LocalDateTime.class);

                LocalDateTime nowLDT = LocalDateTime.now();

                long differenceInMinutes = ChronoUnit.MINUTES.between(dvVakcinacije, nowLDT);

                if (differenceInMinutes <= 6) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Time between doses hasn't passed (6 months)!");
                    return "redirect:/prijaveZaVakcinu";
                }
            }

            if (count == 1) {
                redirectAttributes.addFlashAttribute("errorMessage", "Second dose hasn't been taken!");
                return "redirect:/prijaveZaVakcinu";
            }

            if (count == 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "First dose hasn't been taken!");
                return "redirect:/prijaveZaVakcinu";
            }
        }

        if (doza == DozaVakcine.CETVRTA) {
            int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Vakcinacija WHERE KorisnikID = ? AND " +
                            "(DozaVakcine = 'PRVA' OR DozaVakcine = 'DRUGA' OR DozaVakcine = 'DozaVakcine' OR EDoza = 'CETVRTA')",
                    new Object[]{korisnikID}, Integer.class);

            if (count == 4) {
                redirectAttributes.addFlashAttribute("errorMessage", "Fourth dose has already been taken!");
                return "redirect:/prijaveZaVakcinu";
            }

            if (count == 3) {
                LocalDateTime dvVakcinacije = jdbcTemplate.queryForObject("SELECT DatumVreme " +
                                "FROM Vakcinacija WHERE KorisnikID = ? AND DozaVakcine = 'TRECA'",
                        new Object[]{korisnikID}, LocalDateTime.class);

                LocalDateTime nowLDT = LocalDateTime.now();

                long differenceInMinutes = ChronoUnit.MINUTES.between(dvVakcinacije, nowLDT);

                System.out.println(dvVakcinacije);
                System.out.println(nowLDT);
                System.out.println(differenceInMinutes);

                if (differenceInMinutes <= 3) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Time between doses hasn't passed (3 months)!");
                    return "redirect:/prijaveZaVakcinu";
                }
            }

            if (count == 2) {
                redirectAttributes.addFlashAttribute("errorMessage", "Third dose hasn't been taken!");
                return "redirect:/prijaveZaVakcinu";
            }

            if (count == 1) {
                redirectAttributes.addFlashAttribute("errorMessage", "Second dose hasn't been taken!");
                return "redirect:/prijaveZaVakcinu";
            }

            if (count == 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "First dose hasn't been taken!");
                return "redirect:/prijaveZaVakcinu";
            }
        }

        jdbcTemplate.update("INSERT INTO Vakcinacija(KorisnikID, VakcinaID, DozaVakcine) values(?, ?, ?)",
                korisnikID, vakcinaID, doza.toString());

        jdbcTemplate.update("UPDATE Vakcina SET dostupnaKolicina = dostupnaKolicina - 1 WHERE VakcinaID = ?",
                vakcinaID);


        jdbcTemplate.update("DELETE FROM PrijavaZaVakcinu WHERE KorisnikID = ?", korisnikID);

        return "redirect:/prijaveZaVakcinu";
    }

}
