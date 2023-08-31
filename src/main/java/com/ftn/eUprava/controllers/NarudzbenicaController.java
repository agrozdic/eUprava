package com.ftn.eUprava.controllers;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ftn.eUprava.models.Narudzbenica;
import com.ftn.eUprava.models.StatusZahteva;
import com.ftn.eUprava.models.Vakcina;
import com.ftn.eUprava.services.NarudzbenicaService;
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


@Controller
@RequestMapping(value="/narudzbenice")
public class NarudzbenicaController implements ServletContextAware {

    @Autowired
    private ServletContext servletContext;
    private  String bURL;

    @Autowired
    private NarudzbenicaService narudzbenicaService;

    @Autowired
    private VakcinaService vakcinaService;


    @PostConstruct
    public void init() {
        bURL = servletContext.getContextPath()+"/";
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ModelAndView index() {
        List<Narudzbenica> narudzbenice = narudzbenicaService.findAll();
        List<Vakcina> vakcine = vakcinaService.findAll();

        ModelAndView rezultat = new ModelAndView("narudzbenice");
        rezultat.addObject("narudzbenice", narudzbenice);
        rezultat.addObject("vakcine", vakcine);

        return rezultat;
    }

    @GetMapping(value="/add")
    public String create(HttpSession session, HttpServletResponse response ) {

        List<Vakcina> vakcine = vakcinaService.findAll();
        session.setAttribute("vakcine", vakcine);

        return "dodavanjeNarudzbenice";
    }
    @SuppressWarnings("unused")
    @PostMapping(value="/add")
    public void create(@RequestParam(value = "vakcinaID") @PathVariable("id") int vakcinaID,
                       @RequestParam int kolicina,

                       @RequestParam(required = false) String komentar,
                       @RequestParam StatusZahteva status,
                       HttpServletResponse response) throws IOException {

        Vakcina vakcina = vakcinaService.findOne(vakcinaID);

        Narudzbenica narudzbenica = new Narudzbenica(vakcina, kolicina, komentar, status);
        Narudzbenica saved = narudzbenicaService.save(narudzbenica);
        response.sendRedirect(bURL + "narudzbenice");
    }

    @SuppressWarnings("unused")
    @PostMapping(value="/edit")
    public void Edit(@RequestParam Integer narudzbenicaID,
                     @RequestParam Integer vakcinaID,
                     @RequestParam Integer kolicina,
                     @RequestParam(required = false) String komentar,
                     @RequestParam StatusZahteva status,
                     HttpServletResponse response) throws IOException {

        Vakcina vakcina  = vakcinaService.findOne(vakcinaID);
        Narudzbenica narudzbenica = narudzbenicaService.findOne(narudzbenicaID);

        if(narudzbenica != null) {
            if(vakcinaID != null)
                narudzbenica.setVakcina(vakcina);
            if(kolicina > 0)
                narudzbenica.setKolicina(kolicina);
            if(komentar != null)
                narudzbenica.setKomentar(komentar);
            if(status != null )
                narudzbenica.setStatus(status);
        }
        Narudzbenica saved = narudzbenicaService.update(narudzbenica);
        response.sendRedirect(bURL + "narudzbenice");

    }

    @PostMapping(value ="/odobri")
    public String odobri(@RequestParam("vakcinaID") Long vakcinaID,
                         @RequestParam("kolicina") Integer kolicina,
                         @RequestParam("status") StatusZahteva status,
                         @RequestParam("komentar") String komentar,
                         @RequestParam("narudzbenicaID") Integer narudzbenicaID) {
        jdbcTemplate.update("UPDATE Vakcina SET DostupnaKolicina = DostupnaKolicina + ? WHERE vakcinaID = ?",
                kolicina, vakcinaID);

        jdbcTemplate.update("UPDATE Narudzbenica SET VakcinaID = ?, Kolicina = ?, Komentar = ?, StatusZahteva = ? WHERE NarudzbenicaID = ?",
                vakcinaID, kolicina, komentar, status.toString(), narudzbenicaID);

        jdbcTemplate.update("DELETE FROM Narudzbenica narudzbenicaID id = ?", narudzbenicaID);

        return "redirect:/narudzbenice";
    }

    @SuppressWarnings("unused")
    @PostMapping(value="/delete")
    public void delete(@RequestParam Integer narudzbenicaID, HttpServletResponse response) throws IOException {
        Narudzbenica deleted = narudzbenicaService.delete(narudzbenicaID);
        response.sendRedirect(bURL+"narudzbenice");
    }

    @GetMapping(value="/details")
    @ResponseBody
    public ModelAndView details(@RequestParam Integer narudzbenicaID) {
        Narudzbenica narudzbenica  = narudzbenicaService.findOne(narudzbenicaID);
        List<Vakcina> vakcine = vakcinaService.findAll();
        ModelAndView rezultat = new ModelAndView("narudzbenica");
        rezultat.addObject("narudzbenica", narudzbenica);
        rezultat.addObject("vakcine", vakcine);

        return rezultat;
    }

}