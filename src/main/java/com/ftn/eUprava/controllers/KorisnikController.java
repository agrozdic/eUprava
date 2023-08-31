package com.ftn.eUprava.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.eUprava.models.Korisnik;

import com.ftn.eUprava.services.KorisnikService;


@Controller
@RequestMapping(value = "/korisnici")
public class KorisnikController implements ServletContextAware {

    public static final String KORISNIK_KEY = "korisnik";

    @Autowired
    private ServletContext servletContext;
    private String bURL;

    @Autowired
    private KorisnikService korisnikService;

    @PostConstruct
    public void init() {
        bURL = servletContext.getContextPath()+"/";
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @GetMapping(value = "/login")
    public void getLogin(@RequestParam(required = false) String email, @RequestParam(required = false) String lozinka,
                         HttpSession session, HttpServletResponse response) throws IOException {
        postLogin(email, lozinka, session, response);
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public void postLogin(@RequestParam(required = false) String email, @RequestParam(required = false) String lozinka,
                          HttpSession session, HttpServletResponse response) throws IOException {

        Korisnik korisnik = korisnikService.findOne(email, lozinka);
        if (korisnik == null) {
            response.getWriter().println("<script>alert('User doesn't exist.'); "
                    + "window.location.href='" + bURL + "registracija.html" + "';</script>");
            return;
        }

        session.setAttribute(KORISNIK_KEY, korisnik);

        response.sendRedirect(bURL + "vakcine");
    }

    @GetMapping(value="/logout")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Korisnik korisnik = (Korisnik) request.getSession().getAttribute(KORISNIK_KEY);

        request.getSession().removeAttribute(KORISNIK_KEY);
        request.getSession().invalidate();
        response.sendRedirect(bURL + "");
    }

    @PostMapping(value="/registracija")
    public ModelAndView registracija(@RequestParam String email,
                                     @RequestParam String lozinka,
                                     @RequestParam String ponovljenaLozinka,
                                     @RequestParam String ime,
                                     @RequestParam String prezime,
                                     @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRodjenja,
                                     @RequestParam String jmbg,
                                     @RequestParam String adresa,
                                     @RequestParam String brojTelefona,
                                     HttpSession session, HttpServletResponse response) throws IOException {
        try {
            Korisnik postojeciKorisnik = korisnikService.findOne(email);
            Korisnik postojeciKorisnikJMBG = korisnikService.findOneByJMBG(jmbg);

            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);

            if (postojeciKorisnik != null ) {
                throw new Exception("User with this email address already exists!");
            }
            if (email.equals("") || !pattern.matcher(email).matches()) {
                throw new Exception("Enter email address in format user@mail.com");
            }
            if (lozinka.equals("")) {
                throw new Exception("Enter password!");
            }
            if (!lozinka.equals(ponovljenaLozinka)) {
                throw new Exception("Passwords do not match!");
            }
            if (ime.equals("")) {
                throw new Exception("Enter first name!");
            }
            if (prezime.equals("")) {
                throw new Exception("Enter last name!");
            }
            if (datumRodjenja == null) {
                throw new Exception("Enter birthday!");
            }

            if (postojeciKorisnikJMBG != null ) {
                throw new Exception("User with this JMBG already exists!");
            }
            if (jmbg.equals("") || jmbg.length() != 13) {
                throw new Exception("Enter JMBG in format XXXXXXXXXXXXX!");
            }

            if (adresa.equals("")) {
                throw new Exception("Enter address!");
            }
            if (brojTelefona.equals("")) {
                throw new Exception("Enter phone number!");
            }

            Korisnik korisnik = new Korisnik(email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brojTelefona);
            korisnikService.save(korisnik);

            response.sendRedirect(bURL + "prijava.html");
            return null;
        } catch (Exception ex) {
            String poruka = ex.getMessage();

            ModelAndView rezultat = new ModelAndView("registracija");
            rezultat.addObject("poruka", poruka);

            return rezultat;
        }
    }

    @GetMapping
    public ModelAndView index() {
        List<Korisnik> korisnici = korisnikService.findAll();

        ModelAndView rezultat = new ModelAndView("korisnici");
        rezultat.addObject("korisnici", korisnici);

        return rezultat;
    }

    @GetMapping(value="/add")
    public String create(HttpSession session, HttpServletResponse response){
        return "dodavanjeKorisnika";
    }

    @SuppressWarnings("unused")
    @PostMapping(value="/add")
    public void create(@RequestParam String email,
                       @RequestParam String lozinka,
                       @RequestParam String ime,
                       @RequestParam String prezime,
                       @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRodjenja,
                       @RequestParam String jmbg,
                       @RequestParam String adresa,
                       @RequestParam String brojTelefona,
                       HttpServletResponse response) throws IOException {

        Korisnik korisnik = new Korisnik(email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brojTelefona);
        Korisnik saved = korisnikService.save(korisnik);
        response.sendRedirect(bURL+"korisnici");
    }

    @SuppressWarnings("unused")
    @PostMapping(value="/edit")
    public void Edit(@RequestParam int korisnikID,
                     @RequestParam String email,
                     @RequestParam String lozinka,
                     @RequestParam String ponovljenaLozinka,@RequestParam String ime,
                     @RequestParam String prezime,
                     @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRodjenja,
                     @RequestParam String jmbg,
                     @RequestParam String adresa,
                     @RequestParam String brojTelefona,
                     HttpServletResponse response) throws IOException {
        Korisnik korisnik = korisnikService.findOneById(korisnikID);
        if(korisnik != null) {
            if(email != null && !email.trim().equals(""))
                korisnik.setEmail(email);

            if(lozinka != null && !lozinka.trim().equals("") && ponovljenaLozinka != null
                    && !ponovljenaLozinka.trim().equals("")) {
                if(!lozinka.equals(ponovljenaLozinka)) {
                    response.getWriter().println("<script>alert('Passwords do not match!'); "
                            + "window.location.href='" + bURL + "korisnici/details?id=" + korisnikID +
                            "';</script>");
                    return;
                } else {
                    korisnik.setLozinka(lozinka);
                }
            }

            if(ime != null && !ime.trim().equals(""))
                korisnik.setIme(ime);
            if(prezime != null && !prezime.trim().equals(""))
                korisnik.setPrezime(prezime);
            if(datumRodjenja != null)
                korisnik.setDatumRodjenja(datumRodjenja);
            if(jmbg != null && !jmbg.trim().equals(""))
                korisnik.setJmbg(jmbg);
            if(adresa != null && !adresa.trim().equals(""))
                korisnik.setAdresa(adresa);
            if(brojTelefona != null && !brojTelefona.trim().equals(""))
                korisnik.setBrojTelefona(brojTelefona);
        }
        Korisnik saved = korisnikService.update(korisnik);
        response.sendRedirect(bURL+"korisnici");
    }

    @SuppressWarnings("unused")
    @PostMapping(value="/delete")
    public void delete(@RequestParam int korisnikID, HttpServletResponse response) throws IOException {
        Korisnik deleted = korisnikService.delete(korisnikID);
        response.sendRedirect(bURL+"korisnici");
    }

    @GetMapping(value="/details")
    @ResponseBody
    public ModelAndView details(@RequestParam int korisnikID) {
        Korisnik korisnik  = korisnikService.findOneById(korisnikID);

        ModelAndView rezultat = new ModelAndView("korisnik");
        rezultat.addObject("korisnik", korisnik);

        return rezultat;
    }

}