package com.ftn.eUprava.controllers;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ftn.eUprava.models.Proizvodjac;
import com.ftn.eUprava.models.Vakcina;
import com.ftn.eUprava.services.ProizvodjacService;
import com.ftn.eUprava.services.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value="/vakcine")
public class VakcinaController implements ServletContextAware {

    @Autowired
    private ServletContext servletContext;
    private String bURL;

    @Autowired
    private VakcinaService vakcinaService;

    @Autowired
    private ProizvodjacService proizvodjacService;


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
            @RequestParam(required = false) String naziv,
            @RequestParam(required = false) Integer dostupnaKolicinaMin,
            @RequestParam(required = false) Integer dostupnaKolicinaMax,
            @RequestParam(required = false) String proizvodjac,
            @RequestParam(required = false) String drzava,

            HttpSession session) throws IOException {

        if(naziv != null && naziv.trim().equals(""))
            naziv = null;

        List<Vakcina> vakcine = vakcinaService.find(naziv, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();

        ModelAndView rezultat = new ModelAndView("vakcine");
        rezultat.addObject("vakcine", vakcine);
        rezultat.addObject("proizvodjaci", proizvodjaci);
        return rezultat;

    }

    @GetMapping(value="/add")
    public String create(HttpSession session, HttpServletResponse response ) {

        List<Proizvodjac> proizvodjac = proizvodjacService.findAll();
        session.setAttribute("proizvodjac", proizvodjac);

        return "dodavanjeVakcine";
    }
    @SuppressWarnings("unused")
    @PostMapping(value="/add")
    public void create(@RequestParam String naziv,
                       @RequestParam(value = "proizvodjacID") @PathVariable("id") Integer proizvodjacID,
                       HttpServletResponse response) throws IOException {

        Proizvodjac proizvodjac = proizvodjacService.findOne(proizvodjacID);

        Vakcina vakcina = new Vakcina(naziv, proizvodjac);
        Vakcina saved = vakcinaService.save(vakcina);
        response.sendRedirect(bURL + "vakcine");
    }

    @SuppressWarnings("unused")
    @PostMapping(value="/edit")
    public void Edit(@RequestParam Integer vakcinaID,
                     @RequestParam String naziv,
                     @RequestParam int dostupnaKolicina,
                     @RequestParam Integer proizvodjacID,
                     HttpServletResponse response) throws IOException {

        Proizvodjac proizvodjac  = proizvodjacService.findOne(proizvodjacID);
        Vakcina vakcina = vakcinaService.findOne(vakcinaID);
        if(vakcina != null) {
            if(naziv != null && !naziv.trim().equals(""))
                vakcina.setNaziv(naziv);
            if(dostupnaKolicina > 0)
                vakcina.setDostupnaKolicina(dostupnaKolicina);
            if(proizvodjacID != null)
            {
                vakcina.setProizvodjac(proizvodjac);
            }

        }
        Vakcina saved = vakcinaService.update(vakcina);
        response.sendRedirect(bURL+"vakcine");
    }

    @SuppressWarnings("unused")
    @PostMapping(value="/delete")
    public void delete(@RequestParam Integer vakcinaID,
                       HttpServletResponse response) throws IOException {
        Vakcina deleted = vakcinaService.delete(vakcinaID);
        response.sendRedirect(bURL+"vakcine");
    }


    @GetMapping(value="/details")
    @ResponseBody
    public ModelAndView details(@RequestParam Integer vakcinaID) {
        Vakcina vakcina  = vakcinaService.findOne(vakcinaID);
        List<Proizvodjac> proizvodjac = proizvodjacService.findAll();
        ModelAndView rezultat = new ModelAndView("vakcina");
        rezultat.addObject("vakcina", vakcina);
        rezultat.addObject("proizvodjac", proizvodjac);

        return rezultat;
    }

    @PostMapping(value ="/search")
    public ModelAndView pretraga( @RequestParam(required = false) String naziv,
                                  @RequestParam(required = false) Integer dostupnaKolicinaMin,
                                  @RequestParam(required = false) Integer dostupnaKolicinaMax,
                                  @RequestParam(required = false) String proizvodjac,
                                  @RequestParam(required = false) String drzava,
                                  HttpServletResponse response) throws IOException {

        ModelAndView rezlutat = new ModelAndView("vakcine");

        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();


        if(naziv !=null && naziv.trim().equals(""))
            naziv = null;

        List<Vakcina> vakcineFilter = vakcinaService.find(naziv, dostupnaKolicinaMin, dostupnaKolicinaMax,  proizvodjac, drzava);
        rezlutat.addObject("vakcine", vakcineFilter);
        rezlutat.addObject("proizvodjac", proizvodjac);
        return rezlutat;

    }
    @PostMapping(value = "/ascIme")
    public ModelAndView ascIme(@RequestParam(required = false) String naziv,
                               @RequestParam(required = false) Integer dostupnaKolicinaMin,
                               @RequestParam(required = false) Integer dostupnaKolicinaMax,
                               @RequestParam(required = false) String proizvodjac,
                               @RequestParam(required = false) String drzava,
                               HttpServletResponse response) throws IOException {
        ModelAndView result = new ModelAndView("vakcine");

        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();

        if (naziv != null && naziv.trim().equals("")) {
            naziv = null;
        }

        List<Vakcina> vakcineFilter = vakcinaService.find(naziv, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
        vakcineFilter.sort(Comparator.comparing(Vakcina::getNaziv));
        result.addObject("vakcine", vakcineFilter);
        result.addObject("proizvodjac", proizvodjac);
        return result;
    }


    @PostMapping(value = "/descIme")
    public ModelAndView descIme(@RequestParam(required = false) String naziv,
                                @RequestParam(required = false) Integer dostupnaKolicinaMin,
                                @RequestParam(required = false) Integer dostupnaKolicinaMax,
                                @RequestParam(required = false) String proizvodjac,
                                @RequestParam(required = false) String drzava,
                                HttpServletResponse response) throws IOException {

        ModelAndView result = new ModelAndView("vakcine");

        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();

        if(naziv !=null && naziv.trim().equals(""))
            naziv =null;

        List<Vakcina> vakcineFilter = vakcinaService.find(naziv, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);

        vakcineFilter.sort(Comparator.comparing(Vakcina::getNaziv).reversed());

        result.addObject("vakcine", vakcineFilter);
        result.addObject("proizvodjac", proizvodjac);
        return result;
    }
    @PostMapping(value = "/ascKolicina")
    public ModelAndView ascKolicina(@RequestParam(required = false) String naziv,
                                    @RequestParam(required = false) Integer dostupnaKolicinaMin,
                                    @RequestParam(required = false) Integer dostupnaKolicinaMax,
                                    @RequestParam(required = false) String proizvodjac,
                                    @RequestParam(required = false) String drzava,
                                    HttpServletResponse response) throws IOException {
        ModelAndView result = new ModelAndView("vakcine");

        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();

        if (naziv != null && naziv.trim().equals("")) {
            naziv = null;
        }

        List<Vakcina> vakcineFilter = vakcinaService.find(naziv, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
        vakcineFilter.sort(Comparator.comparing(Vakcina::getDostupnaKolicina));
        result.addObject("vakcine", vakcineFilter);
        result.addObject("proizvodjac", proizvodjac);
        return result;
    }


    @PostMapping(value = "/descKolicina")
    public ModelAndView descKolicina(@RequestParam(required = false) String naziv,
                                     @RequestParam(required = false) Integer dostupnaKolicinaMin,
                                     @RequestParam(required = false) Integer dostupnaKolicinaMax,
                                     @RequestParam(required = false) String proizvodjac,
                                     @RequestParam(required = false) String drzava,
                                     HttpServletResponse response) throws IOException {

        ModelAndView result = new ModelAndView("vakcine");

        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();

        if(naziv !=null && naziv.trim().equals(""))
            naziv =null;

        List<Vakcina> vakcineFilter = vakcinaService.find(naziv, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);

        vakcineFilter.sort(Comparator.comparing(Vakcina::getDostupnaKolicina).reversed());

        result.addObject("vakcine", vakcineFilter);
        result.addObject("proizvodjac", proizvodjac);
        return result;
    }
    @PostMapping(value = "/ascProizvodjac")
    public ModelAndView ascProizvodjac(@RequestParam(required = false) String naziv,
                                       @RequestParam(required = false) Integer dostupnaKolicinaMin,
                                       @RequestParam(required = false) Integer dostupnaKolicinaMax,
                                       @RequestParam(required = false) String proizvodjac,
                                       @RequestParam(required = false) String drzava,
                                       HttpServletResponse response) throws IOException {
        ModelAndView result = new ModelAndView("vakcine");

        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();

        if (naziv != null && naziv.trim().equals("")) {
            naziv = null;
        }

        List<Vakcina> vakcineFilter = vakcinaService.find(naziv, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
        vakcineFilter.sort(Comparator.comparing(vakcina -> vakcina.getProizvodjac().getNaziv().toString()));

        result.addObject("vakcine", vakcineFilter);
        result.addObject("proizvodjac", proizvodjac);
        return result;
    }


    @PostMapping(value = "/descProizvodjac")
    public ModelAndView descProizvodjac(@RequestParam(required = false) String naziv,
                                        @RequestParam(required = false) Integer dostupnaKolicinaMin,
                                        @RequestParam(required = false) Integer dostupnaKolicinaMax,
                                        @RequestParam(required = false) String proizvodjac,
                                        @RequestParam(required = false) String drzava,
                                        HttpServletResponse response) throws IOException {

        ModelAndView result = new ModelAndView("vakcine");

        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();

        if(naziv !=null && naziv.trim().equals(""))
            naziv =null;

        List<Vakcina> vakcineFilter = vakcinaService.find(naziv, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);

        Comparator<Vakcina> proizvodjacComparator = Comparator.comparing(vakcina -> vakcina.getProizvodjac().getNaziv().toString(),
                Comparator.reverseOrder());
        vakcineFilter.sort(proizvodjacComparator);

        result.addObject("vakcine", vakcineFilter);
        result.addObject("proizvodjac", proizvodjac);
        return result;
    }
    @PostMapping(value = "/ascDrzava")
    public ModelAndView ascDrzava(@RequestParam(required = false) String naziv,
                                  @RequestParam(required = false) Integer dostupnaKolicinaMin,
                                  @RequestParam(required = false) Integer dostupnaKolicinaMax,
                                  @RequestParam(required = false) String proizvodjac,
                                  @RequestParam(required = false) String drzava,
                                  HttpServletResponse response) throws IOException {
        ModelAndView result = new ModelAndView("vakcine");

        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();

        if (naziv != null && naziv.trim().equals("")) {
            naziv = null;
        }

        List<Vakcina> vakcineFilter = vakcinaService.find(naziv, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
        vakcineFilter.sort(Comparator.comparing(vakcina -> vakcina.getProizvodjac().getDrzava().toString()));

        result.addObject("vakcine", vakcineFilter);
        result.addObject("proizvodjac", proizvodjac);
        return result;
    }


    @PostMapping(value = "/descDrzava")
    public ModelAndView descDrzava(@RequestParam(required = false) String naziv,
                                   @RequestParam(required = false) Integer dostupnaKolicinaMin,
                                   @RequestParam(required = false) Integer dostupnaKolicinaMax,
                                   @RequestParam(required = false) String proizvodjac,
                                   @RequestParam(required = false) String drzava,
                                   HttpServletResponse response) throws IOException {

        ModelAndView result = new ModelAndView("vakcine");

        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();

        if(naziv !=null && naziv.trim().equals(""))
            naziv =null;

        List<Vakcina> vakcineFilter = vakcinaService.find(naziv, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);

        Comparator<Vakcina> proizvodjacComparator = Comparator.comparing(vakcina -> vakcina.getProizvodjac().getNaziv().toString(),
                Comparator.reverseOrder());
        vakcineFilter.sort(proizvodjacComparator);

        result.addObject("vakcine", vakcineFilter);
        result.addObject("proizvodjac", proizvodjac);
        return result;
    }
}