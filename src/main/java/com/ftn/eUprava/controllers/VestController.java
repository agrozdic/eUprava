package com.ftn.eUprava.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ftn.eUprava.services.VestService;
import com.ftn.eUprava.models.Vest;
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


@Controller
@RequestMapping(value= "/vesti")
public class VestController implements ServletContextAware {


    @Autowired
    private ServletContext servletContext;
    private  String bURL;

    @Autowired
    private VestService vestService;


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
        List<Vest> vesti = vestService.findAll();

        ModelAndView rezultat = new ModelAndView("vesti");
        rezultat.addObject("vesti", vesti);

        return rezultat;
    }

    @GetMapping(value="/add")
    public String create(HttpSession session, HttpServletResponse response){
        return "dodavanjeVesti";
    }


    @SuppressWarnings("unused")
    @PostMapping(value="/add")
    public void create(@RequestParam(required = true) String naslov,
                       @RequestParam(required = true) String sadrzaj,
                       @RequestParam(required = true) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumVreme,
                       HttpServletResponse response) throws IOException {
        Vest vest = new Vest(naslov, sadrzaj, datumVreme);
        Vest saved = vestService.save(vest);
        response.sendRedirect(bURL + "vesti");
    }

    @SuppressWarnings("unused")
    @PostMapping(value="/edit")
    public void Edit(@RequestParam Integer vestID,
                     @RequestParam(required = true) String naslov,
                     @RequestParam(required = true) String sadrzaj,
                     @RequestParam(required = true) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumVreme,
                     HttpServletResponse response) throws IOException {
        Vest vest = vestService.findOne(vestID);
        if(vest != null) {
            if(naslov != null && !naslov.trim().equals(""))
                vest.setNaslov(naslov);
            if(sadrzaj != null && !sadrzaj.trim().equals(""))
                vest.setSadrzaj(sadrzaj);
            if(datumVreme != null)
                vest.setDvObjave(datumVreme);
        }
        Vest saved = vestService.update(vest);
        response.sendRedirect(bURL + "vesti");
    }

    @SuppressWarnings("unused")
    @PostMapping(value="/delete")
    public void delete(@RequestParam Integer vestID,
                       HttpServletResponse response) throws IOException {
        Vest deleted = vestService.delete(vestID);
        response.sendRedirect(bURL + "vesti");
    }

    @GetMapping(value="/details")
    @ResponseBody
    public ModelAndView details(@RequestParam Integer vestID) {
        Vest vest = vestService.findOne(vestID);


        ModelAndView rezultat = new ModelAndView("vest");
        rezultat.addObject("vest", vest);

        return rezultat;
    }

}