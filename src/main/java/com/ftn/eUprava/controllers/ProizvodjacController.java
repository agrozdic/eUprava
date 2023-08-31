package com.ftn.eUprava.controllers;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ftn.eUprava.models.Proizvodjac;
import com.ftn.eUprava.services.ProizvodjacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value="/proizvodjaci")
public class ProizvodjacController implements ServletContextAware {

    @Autowired
    private ServletContext servletContext;
    private  String bURL;

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
    public ModelAndView index() {
        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();

        ModelAndView rezultat = new ModelAndView("proizvodjaci");
        rezultat.addObject("proizvodjaci", proizvodjaci);

        return rezultat;
    }

    @GetMapping(value="/add")
    public String create(HttpSession session, HttpServletResponse response){
        return "dodavanjeProizvodjaca";
    }
    
    @SuppressWarnings("unused")
    @PostMapping(value="/add")
    public void create(@RequestParam(required = true) String naziv,
                       @RequestParam(required = true) String drzava,
                       HttpServletResponse response) throws IOException {
        Proizvodjac proizvodjac = new Proizvodjac(naziv, drzava);
        Proizvodjac saved = proizvodjacService.save(proizvodjac);
        response.sendRedirect(bURL + "proizvodjaci");
    }

    @SuppressWarnings("unused")
    @PostMapping(value="/edit")
    public void Edit(@RequestParam Integer proizvodjacID,
                     @RequestParam(required = true) String naziv,
                     @RequestParam(required = true) String drzava,
                     HttpServletResponse response) throws IOException {
        Proizvodjac proizvodjac = proizvodjacService.findOne(proizvodjacID);
        if(proizvodjac != null) {
            if((proizvodjac != null) && (!naziv.trim().equals("")))
                proizvodjac.setNaziv(naziv);
            if((drzava != null) && (!drzava.trim().equals("")))
                proizvodjac.setDrzava(drzava);

        }
        Proizvodjac saved = proizvodjacService.update(proizvodjac);
        response.sendRedirect(bURL + "proizvodjaci");
    }


    @SuppressWarnings("unused")
    @PostMapping(value="/delete")
    public void delete(@RequestParam Integer proizvodjacID, HttpServletResponse response) throws IOException {
        Proizvodjac deleted = proizvodjacService.delete(proizvodjacID);
        response.sendRedirect(bURL + "proizvodjaci");
    }

    @GetMapping(value="/details")
    @ResponseBody
    public ModelAndView details(@RequestParam Integer proizvodjacID) {
        Proizvodjac proizvodjac  = proizvodjacService.findOne(proizvodjacID);


        ModelAndView rezultat = new ModelAndView("proizvodjac");
        rezultat.addObject("proizvodjac", proizvodjac);

        return rezultat;
    }

}