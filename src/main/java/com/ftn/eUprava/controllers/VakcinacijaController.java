package com.ftn.eUprava.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import com.ftn.eUprava.services.VakcinacijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.eUprava.models.Vakcinacija;

@Controller
@RequestMapping(value="/vakcinacije")
public class VakcinacijaController implements ServletContextAware {

    @Autowired
    private ServletContext servletContext;
    private  String bURL;

    @Autowired
    private VakcinacijaService vakcinacijaService;


    @PostConstruct
    public void init() {
        bURL = servletContext.getContextPath()+ "/";
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


    @GetMapping
    public ModelAndView index() {
        List<Vakcinacija> vakcinacije = vakcinacijaService.findAll();

        ModelAndView rezultat = new ModelAndView("vakcinacije");
        rezultat.addObject("vakcinacije", vakcinacije);

        return rezultat;
    }
}
