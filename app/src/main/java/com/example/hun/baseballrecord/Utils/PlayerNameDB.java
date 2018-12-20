package com.example.hun.baseballrecord.Utils;

import java.util.HashMap;

public class PlayerNameDB {

    public String PlayerDB(String name){

        HashMap<String , String> map = new HashMap<>();
        map.put("오재원", "77248");
        map.put("김재호", "74206");
        map.put("허경민", "79240");
        map.put("류지혁", "62234");
        map.put("전민재", "68205");
        map.put("국해성", "79290");
        map.put("장승현", "63202");
        map.put("신성현", "64086");
        map.put("황경태", "66207");
        map.put("박세혁", "62244");
        map.put("김재환", "78224");
        map.put("오재일", "75334");
        map.put("린드블럼", "65543");
        map.put("함덕주", "63248");


        return map.get(name);
    }
}
