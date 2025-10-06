package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class MainController {
    List<String> citat = readAllLines("citaty.txt");


    public MainController() throws IOException {
    }

    @GetMapping( "/")
    public ModelAndView citat() throws IOException {
        ModelAndView modelAndView = new ModelAndView("citat");
        modelAndView.addObject("nahodnyCitat",getNahodnyCitat(citat));
        modelAndView.addObject("nahodnyIndex",nahodnyIndex(citat));
        return modelAndView;
    }



    private static List<String> readAllLines(String resource)throws IOException {     //metoda readAllLines nacte vsechny radky textoveho souboru ulozeneho v resources a vrati jako seznam retezcu List<String>
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();  //nacte soubory z resource balicku

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try(InputStream inputStream=classLoader.getResourceAsStream(resource); //parametr resources je nazev souboru odkud se data nacitaji pr.citaty.txt
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){  //umoznuje cist soubor jako text v UTF-8 kodovani

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }


    public static String getNahodnyCitat (List<String> citaty) {  //metoda, ktera vybere nahodne citat ze seznamu
        Random random = new Random();
        int index = random.nextInt(citaty.size());
        return citaty.get(index);
    }


    public static int nahodnyIndex (List<String> citaty) {   //metoda pro generovani indexu obrazku
        Random random = new Random();
        int i = random.nextInt(citaty.size()) + 1;
        return i;
    }





}
