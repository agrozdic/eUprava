package com.ftn.eUprava.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
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

import com.ftn.eUprava.models.OboleliVest;
import com.ftn.eUprava.services.OboleliVestService;

@Controller
@RequestMapping(value="/oboleliVest")
public class OboleliVestController implements ServletContextAware {


    @Autowired
    private ServletContext servletContext;
    private  String bURL;

    @Autowired
    private OboleliVestService oboleliVestService;


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
        List<OboleliVest> oboleliVest = oboleliVestService.findAll();

        ModelAndView rezultat = new ModelAndView("oboleliVest");
        rezultat.addObject("oboleliVest", oboleliVest);
        rezultat.addObject("currentDate", LocalDateTime.now());

        return rezultat;
    }


    @GetMapping(value="/add")
    public String create(HttpSession session, HttpServletResponse response){
        return "dodavanjeOboleliVest";
    }


    @SuppressWarnings("unused")
    @PostMapping(value="/add")
    public void create(@RequestParam(required = true) int brObolelih,
                       @RequestParam(required = true) int brTestiranih,
                       @RequestParam(required = true) int brHospitalizovanih,
                       @RequestParam(required = true) int brNaRespiratoru,
                       @RequestParam(required = true) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumVreme,
                       HttpServletResponse response) throws IOException {
        OboleliVest oboleliVest = new OboleliVest(brObolelih, brTestiranih,
                brHospitalizovanih, brNaRespiratoru, datumVreme);
        OboleliVest saved = oboleliVestService.save(oboleliVest);
        response.sendRedirect(bURL + "oboleliVest");
    }

    @SuppressWarnings("unused")
    @PostMapping(value="/edit")
    public void Edit(@RequestParam Integer oboleliVestID,
                     @RequestParam(required = true) int brObolelih,
                     @RequestParam(required = true) int brTestiranih,
                     @RequestParam(required = true) int brHospitalizovanih,
                     @RequestParam(required = true) int brNaRespiratoru,
                     @RequestParam(required = true) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumVreme,
                     HttpServletResponse response) throws IOException {
        OboleliVest oboleliVest = oboleliVestService.findOne(oboleliVestID);
        if(oboleliVest != null) {
            if(brObolelih > 0)
                oboleliVest.setBrObolelih(brObolelih);
            if(brTestiranih > 0)
                oboleliVest.setBrTestiranih(brTestiranih);
            if(brHospitalizovanih > 0)
                oboleliVest.setBrHospitalizovanih(brHospitalizovanih);
            if(brNaRespiratoru > 0)
                oboleliVest.setBrNaRespiratoru(brNaRespiratoru);
            if(datumVreme != null)
                oboleliVest.setDvObjave(datumVreme);
        }
        OboleliVest saved = oboleliVestService.update(oboleliVest);
        response.sendRedirect(bURL+"oboleliVest");
    }


    @SuppressWarnings("unused")
    @PostMapping(value="/delete")
    public void delete(@RequestParam Integer oboleliVestID, HttpServletResponse response) throws IOException {
        OboleliVest deleted = oboleliVestService.delete(oboleliVestID);
        response.sendRedirect(bURL+"oboleliVest");
    }

    @GetMapping(value="/details")
    @ResponseBody
    public ModelAndView details(@RequestParam Integer oboleliVestID) {
        OboleliVest oboleliVest = oboleliVestService.findOne(oboleliVestID);


        ModelAndView rezultat = new ModelAndView("oboleliVest");
        rezultat.addObject("oboleliVest", oboleliVest);

        return rezultat;
    }

}