package io.thekraken.grok.api;

import io.thekraken.grok.api.exception.GrokException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CasLogTest {

    public final static String LOG_FILE = "src/test/resources/multiline_log";

    @Test
    public void test001_opatch_cas_log() throws GrokException, IOException {
        Grok g = Grok.create(ResourceManager.LOG_4_JAVA_PATTERNS, "%{TIME}\\s+%{JAVAFILE}\\s+%{OLOGLEVEL}\\s+%{GREEDYDATA}");
        
        BufferedReader br = new BufferedReader(new FileReader(LOG_FILE));
        System.out.println("Starting test with opatch cas log");
        Match gm = new Match();
        Match preGm = null;
        String curLine = null;
        List<Match> matches = new ArrayList<Match>();
        while ((curLine =br.readLine()) != null ) {
//            System.out.println(curLine);
                if(!g.isMatch(curLine)){
                    g.match(curLine,gm);
                    continue;
               }else{
                   gm = new Match();
                   g.match(curLine,gm);
                   
               }
                matches.add(gm);
        }
        capture(matches);
        br.close();
    }

    
    private void capture(List<Match> matches){
        for(Match gm : matches){
        gm.captures();
        System.out.println(gm.toJson());
        assertNotNull(gm.toJson());
        }
    }
}
