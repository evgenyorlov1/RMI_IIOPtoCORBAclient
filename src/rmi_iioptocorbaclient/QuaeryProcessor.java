/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi_iioptocorbaclient;


import java.util.ArrayList;
import java.util.regex.*;

/**
 *
 * @author pc
 */

//Refactor pattern methods
public class QuaeryProcessor {  
    
    private static final String none = "none";
       
    //TESTED
    //[0] - command code
    //[1] - table name
    //[2] - key
    //[3] - order
    public static Object[] parse(String line) {
        System.out.println("QuaeryProcessor.parse");       
        
        if(clear(line)) {             
            return new Object[] {0}; //0
        } else if(show_dbs(line)) {
            return new Object[] {1}; //1            
        } else if((boolean)use(line)[0]) {              
            Object[] result = use(line);
            result[0] = 2; //2            
            return result;
        } else if((boolean)load(line)[0]) {
            Object[] result = load(line);
            result[0] = 3; //3
            return result;
        } else if(db(line)) {            
            return new Object[] {10}; //10
        } else if(show_tables(line)) {
            return new Object[] {11}; //11
        } else if(drop_database(line)) {
            return new Object[] {12}; //12
        } else if((boolean)create_table(line)[0]) {            
            Object[] result = create_table(line);
            result[0] = 13; //13
            return result;
        } else if((boolean)save(line)[0]) {
            Object[] result = save(line);
            result[0] = 14; //14            
            return result; //14  
        } else if((boolean)load_table(line)[0]) {
            Object[] result = load_table(line);
            result[0] = 15; //15            
            return result; //15 
        } else if((boolean)insert(line)[0]) {
            Object[] result = insert(line);
            result[0] = 16; //16           
            return result; //16                    
        } else if((boolean)drop(line)[0]) {
            Object[] result = drop(line);
            result[0] = 101; //101
            return result;
        } else if((boolean)find(line)[0]) { 
            Object[] result = find(line);
            result[0] = 102; //102
            return result;            
        } else if((boolean)count(line)[0]) { 
            Object[] result = count(line);
            result[0] = 104; //104
            return result;  
        } else if((boolean)save_table(line)[0]) {
            Object[] result = save_table(line);
            result[0] = 103; //103
            return result;            
        } else if((boolean)update(line)[0]) {
            Object[] result = update(line);
            result[0] = 105; //105
            return result; 
        } else if((boolean)remove_id(line)[0]) {
            Object[] result = remove_id(line);
            result[0] = 106; //106           
            return result; 
        } else if((boolean)remove_key(line)[0]) {
            Object[] result = remove_key(line);
            result[0] = 107; //107         
            return result; 
        } else if((boolean)limit(line)[0]) {
            Object[] result = limit(line);
            result[0] = 1001; //1001
            return result;
        } else if((boolean)sort(line)[0]) {            
            Object[] result = sort(line);
            result[0] = 1002; //1002
            return result;
        } else if((boolean)skip(line)[0]) {
            Object[] result = skip(line);
            result[0] = 1003; //1003
            return result;
        } else {                        
            return new Object[] {999}; //999
        }        
    }    
    
    //TESTE
    public static String useState(String line, String use) {
        System.out.println("QuaeryProcessor.useState");
        Pattern p = Pattern.compile("use [a-zA-Z0-9]+$");  
        Matcher m = p.matcher(line);        
        if(m.matches()) {
            return line.substring(4);
        } else {
            return use;
        }
    }
    
    //clear
    //-
    private static boolean clear(String testString) {  
        System.out.println("QuaeryProcessor.clear");
        Pattern p = Pattern.compile("^clear$");  
        Matcher m = p.matcher(testString);         
        return m.matches(); 
    }
    
    //show dbs 
    //-
    private static boolean show_dbs(String testString) { 
        System.out.println("QuaeryProcessor.show_dbs");
        Pattern p = Pattern.compile("^show dbs$");  
        Matcher m = p.matcher(testString);         
        return m.matches(); 
    }
    
    //use db_name 
    //table name 
    //TESTED
    private static Object[] use(String testString) { 
        System.out.println("QuaeryProcessor.use");
        Object[] result = {false, none};       
        
        Pattern pattern = Pattern.compile("^use [a-zA-Z0-9]+$");
        Pattern name = Pattern.compile("(?<=use )(.*)(?=$)");  
        
        Matcher patternMatch = pattern.matcher(testString);  
        Matcher nameMatch = name.matcher(testString);  
        
        if(patternMatch.matches()) {
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();
        }             
        
        return result;
    }
    
    //db 
    //-
    private static boolean db(String testString) { 
        System.out.println("QuaeryProcessor.db");
        Pattern p = Pattern.compile("^db$");  
        Matcher m = p.matcher(testString);         
        return m.matches(); 
    }
    
    //show tables 
    //-
    private static boolean show_tables(String testString) {  
        System.out.println("QuaeryProcessor.show_tables");
        Pattern p = Pattern.compile("^show tables$");  
        Matcher m = p.matcher(testString);         
        return m.matches(); 
    }
    
    //db.dropDatabase() 
    //-
    private static boolean drop_database(String testString) {  
        System.out.println("QuaeryProcessor.drop_database");
        Pattern p = Pattern.compile("^db\\.dropDatabase\\(\\)$");  
        Matcher m = p.matcher(testString);         
        return m.matches(); 
    }
    
    //db.createTable(name, {key1:type, key2:type, key3:type, ###})
    //table name
    //TESTED
    private static Object[] create_table(String testString) { 
        System.out.println("QuaeryProcessor.create_table");
        ArrayList<String[]> keyType = new ArrayList<String[]>();
        Object[] result = {false, none, keyType};        
        
        Pattern pattern = Pattern.compile("^db\\.createTable\\(.*\\)$");
        Pattern name = Pattern.compile("(?<=^db\\.createTable\\()([a-zA-Z]+)(?=\\,)");
        Pattern kType = Pattern.compile("(?<=\\{)(.*)(?=\\})");         
                
        Matcher patternMatch = pattern.matcher(testString);
        Matcher nameMatch = name.matcher(testString);         
        Matcher kTypeMatch = kType.matcher(testString);                         
        
        System.out.println("patternMatch: " + patternMatch.matches());
        System.out.println("nameMatch: " + nameMatch.matches());
        System.out.println("kTypeMatch: " + kTypeMatch.matches());
        
        if(patternMatch.matches()) {
            kTypeMatch.find();
            String[] keys = kTypeMatch.group().split(",");                 
            for(String kt : keys) {
                keyType.add(kt.split(":"));
            }       
            
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();
        }
        
        return result; 
    }
    
    //db.save() 
    //-
    private static Object[] save(String testString) {  
        System.out.println("QuaeryProcessor.save");
        Object[] result = {false, none};
        Pattern pattern = Pattern.compile("^db\\.save\\([a-zA-Z0-9]+\\)$");          
        Pattern file = Pattern.compile("(?<=save\\()(.*)(?=\\)$)");
        
        Matcher patternMatch = pattern.matcher(testString);         
        Matcher fileMatch = file.matcher(testString); 

        if(patternMatch.matches()) {
            result[0] = true;
            fileMatch.find();
            result[1] = fileMatch.group();            
            System.out.println("group:" + fileMatch.group());
        }              
        
        return result; 
    }
    
    private static Object[] save_table(String testString) { 
        System.out.println("QuaeryProcessor.save_table");
        Object[] result = {false, none, none};
        
        Pattern pattern = Pattern.compile("^db\\.[a-zA-Z0-9]+\\.save\\([a-zA-Z0-9]+\\)$");  
        Pattern name = Pattern.compile("(?<=db\\.)(.*)(?=\\.save\\([a-zA-Z]+\\)$)");  
        Pattern file = Pattern.compile("(?<=save\\()(.*)(?=\\)$)");
        
        Matcher patternMatch = pattern.matcher(testString);         
        Matcher nameMatch = name.matcher(testString);   
        Matcher fileMatch = file.matcher(testString); 
        
        if(patternMatch.matches()) {
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();
            fileMatch.find();
            result[2] = fileMatch.group();
        }
        
        return result; 
    }
    
    private static Object[] load(String testString) {
        System.out.println("QuaeryProcessor.load");
        Object[] result = {false, none};
        
        Pattern pattern = Pattern.compile("^load\\([a-zA-Z0-9]+\\)$");
        Pattern file = Pattern.compile("(?<=load\\()(.*)(?=\\)$)");
                
        Matcher patternMatch = pattern.matcher(testString);                 
        Matcher fileMatch = file.matcher(testString); 
        
        if(patternMatch.matches()) {
            result[0] = true;            
            fileMatch.find();
            result[1] = fileMatch.group();
        }
        
        return result;
    }
    
    private static Object[] load_table(String testString) {
        System.out.println("QuaeryProcessor.load_table");
        Object[] result = {false, none};
        
        Pattern pattern = Pattern.compile("^db.load\\([a-zA-Z0-9]+\\)$");
        Pattern file = Pattern.compile("(?<=db.load\\()(.*)(?=\\)$)");
                
        Matcher patternMatch = pattern.matcher(testString);                 
        Matcher fileMatch = file.matcher(testString); 
        
        if(patternMatch.matches()) {
            result[0] = true;            
            fileMatch.find();
            result[1] = fileMatch.group();
        }
        return result;
    }
    
    //db.###.drop() 
    //table name
    //TESTED
    private static Object[] drop(String testString) { 
        System.out.println("QuaeryProcessor.drop");
        Object[] result = {false, none};
        
        Pattern pattern = Pattern.compile("^db\\.[a-zA-Z0-9]+\\.drop\\(\\)$");  
        Pattern name = Pattern.compile("(?<=db\\.)(.*)(?=\\.drop\\(\\)$)");  
        
        Matcher patternMatch = pattern.matcher(testString);
        Matcher nameMatch = name.matcher(testString);
        
        if(patternMatch.matches()) {
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();
        }
        
        return result; 
    }
    
    //db.###.insert({key1:value,key2:value,key3:value})
    private static Object[] insert(String testString) {
        System.out.println("QuaeryProcessor.insert");
        ArrayList<String[]> keyValue = new ArrayList<String[]>();
        Object[] result = {false, none, keyValue};                
        
        Pattern pattern = Pattern.compile("^db\\.[a-zA-Z0-9]+\\.insert\\(.*\\)$");  
        Pattern name = Pattern.compile("(?<=db\\.)(.*)(?=\\.insert)");             //table 
        Pattern kValue = Pattern.compile("(?<=\\.insert\\(\\{)(.*)(?=\\})");       //keyvalue
        
        Matcher patternMatch = pattern.matcher(testString);
        Matcher nameMatch = name.matcher(testString);                
        Matcher kValueMatch = kValue.matcher(testString);                                 
        
        if(patternMatch.matches()) {
            kValueMatch.find();
            String[] keys = kValueMatch.group().split(",");
            for(String k : keys) {
                keyValue.add(k.split(":"));
            }
            
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();            
        }      
        
        return result; 
    }
    
    //db.###.find() 
    //ADD queries
    //table name
    //TESTED
    private static Object[] find(String testString) { 
        System.out.println("QuaeryProcessor.find");
        Object[] result = {false, none};
        
        Pattern pattern = Pattern.compile("^db\\.[a-zA-Z0-9]+\\.find\\(\\)$");  
        Pattern name = Pattern.compile("(?<=db\\.)(.*)(?=\\.find\\(\\)$)");  
        
        Matcher patternMatch = pattern.matcher(testString);
        Matcher nameMatch = name.matcher(testString);
        
        if(patternMatch.matches()) {
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();
        }
        
        return result; 
    }
    
    //db.###.remove(_id:###)    
    //TESTED
    private static Object[] remove_id(String testString) {
        System.out.println("QuaeryProcessor.remove_id");
        Object[] result = {false, none, none};
        
        Pattern pattern = Pattern.compile("^db\\.[a-zA-Z0-9]+\\.remove\\(_id:[0-9]+\\)$");  
        Pattern name = Pattern.compile("(?<=db\\.)(.*)(?=\\.remove\\()");          //table
        Pattern id = Pattern.compile("(?<=\\.remove\\(_id:)(.*)(?=\\))");          //id
        
        Matcher patternMatch = pattern.matcher(testString);
        Matcher nameMatch = name.matcher(testString);
        Matcher idMatch = id.matcher(testString);
        
        if(patternMatch.matches()) {
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();            
            idMatch.find();
            result[2] = Integer.parseInt(idMatch.group());            
        }
        
        return result;
    }
    
    //TESTED
    //db.###.remove({key1:value,key2:value,key3:value})
    private static Object[] remove_key(String testString) {
        System.out.println("QuaeryProcessor.remove_key");
        ArrayList<String[]> keyValue = new ArrayList<String[]>();
        Object[] result = {false, none, keyValue};        
        
        Pattern pattern = Pattern.compile("^db\\.[a-zA-Z0-9]+\\.remove\\(\\{[a-zA-Z0-9:,.]+\\}\\)$");  
        Pattern name = Pattern.compile("(?<=db\\.)(.*)(?=\\.remove\\()");          //table
        Pattern keyVal = Pattern.compile("(?<=\\.remove\\(\\{)(.*)(?=\\}\\))");    //keyvalue
        
        Matcher patternMatch = pattern.matcher(testString);
        Matcher nameMatch = name.matcher(testString);
        Matcher keyValMatch = keyVal.matcher(testString);
        
        if(patternMatch.matches()) {
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();            
            keyValMatch.find();
            String[] keys = keyValMatch.group().split(",");
            for(String k : keys) {
                keyValue.add(k.split(":"));                
            }
        }
        
        return result;
    }
    
    //db.###.update({_id:###},{key1:value,key2:value,key3:value})
    //TODO
    private static Object[] update(String testString) {
        System.out.println("QuaeryProcessor.update");
        ArrayList<String[]> keyValue = new ArrayList<String[]>();
        Object[] result = {false, none, none, keyValue};        
        
        Pattern pattern = Pattern.compile("^db\\.[a-zA-Z0-9]+\\.update\\(.*\\)$");  
        Pattern name = Pattern.compile("(?<=db\\.)(.*)(?=\\.update\\()");
        Pattern keyVal = Pattern.compile("(?<=\\.update\\()(.*)(?=\\))");    //keyvalue                
        
        Matcher patternMatch = pattern.matcher(testString);
        Matcher nameMatch = name.matcher(testString);
        Matcher keyValMatch = keyVal.matcher(testString);
        
        if(patternMatch.matches()) {
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();            
            keyValMatch.find();
            String[] keys = keyValMatch.group().split(",");
            for(String k : keys) {
                k = k.replaceAll("[{}]" , "");                 
                keyValue.add(k.split(":"));
            }
        }
        for(String[] k : keyValue) {
            if(k[0].equals("_id")) {
                result[2] = k[1];
                keyValue.remove(k);
                break;
            }
        }         
        return result;
    }
    
    private static Object[] count(String testString) {
        System.out.println("QuaeryProcessor.count");
        Object[] result = {false, none};
        
        Pattern pattern = Pattern.compile("^db\\.[a-zA-Z0-9]+\\.count\\(\\)$");  
        Pattern name = Pattern.compile("(?<=db\\.)(.*)(?=\\.count\\(\\)$)");  
        
        Matcher patternMatch = pattern.matcher(testString);
        Matcher nameMatch = name.matcher(testString);
        
        if(patternMatch.matches()) {
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();
        }
        
        return result;
    }
    
    //db.###.find().limit(###) 
    //table name
    //number
    //TESTED
    private static Object[] limit(String testString) { 
        System.out.println("QuaeryProcessor.limit");
        Object[] result = {false, none, none};
        
        Pattern pattern = Pattern.compile("^db\\.[a-zA-Z0-9]+\\.find\\(\\)\\.limit\\([0-9]+\\)$");  
        Pattern name = Pattern.compile("(?<=db\\.)(.*)(?=\\.find\\(\\)\\.limit\\()");
        Pattern number = Pattern.compile("(?<=\\.find\\(\\)\\.limit\\()(.*)(?=\\)$)");
        
        Matcher patternMatch = pattern.matcher(testString);
        Matcher nameMatch = name.matcher(testString);
        Matcher numberMatch = number.matcher(testString);
        
        if(patternMatch.matches()) {
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();
            numberMatch.find();
            result[2] = Integer.parseInt(numberMatch.group());
        }
        
        return result; 
    }    
        
    //db.###.find().sort({key:[-1;1]})     
    //table name
    //key
    //order int
    //TESTED
    private static Object[] sort(String testString) {
        System.out.println("QuaeryProcessor.sort");
        Object[] result = {false, none, none, none};
        
        Pattern pattern = Pattern.compile("^db\\.[a-zA-Z0-9]+\\.find\\(\\)\\.sort\\(\\{[a-zA-Z0-9]+\\:(-1|1)\\}\\)$");
        Pattern name = Pattern.compile("(?<=db\\.)(.*)(?=\\.find\\(\\)\\.sort\\()");  //table
        Pattern key = Pattern.compile("(?<=\\.find\\(\\)\\.sort\\(\\{)(.*)(?=\\:)");  //key
        Pattern order = Pattern.compile("(?<=\\:)(.*)(?=\\}\\)$)");                   //order
        
        Matcher patternMatch = pattern.matcher(testString);        
        Matcher nameMatch = name.matcher(testString);
        Matcher keyMatch = key.matcher(testString);
        Matcher orderMatch = order.matcher(testString);
        
        if(patternMatch.matches()) {
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();
            keyMatch.find();
            result[2] = keyMatch.group();
            orderMatch.find();
            result[3] = Integer.parseInt(orderMatch.group());
        }
        
        return result; 
    }
    
    //db.###.find().skip(###) 
    //table name
    //number int
    //TESTED
    private static Object[] skip(String testString) {
        System.out.println("QuaeryProcessor.skip");
        Object[] result = {false, none, none};
        
        Pattern pattern = Pattern.compile("^db\\.[a-zA-Z0-9]+\\.find\\(\\)\\.skip\\([0-9]+\\)$"); 
        Pattern name = Pattern.compile("(?<=db\\.)(.*)(?=\\.find\\(\\)\\.)");
        Pattern number = Pattern.compile("(?<=\\.find\\(\\)\\.skip\\()(.*)(?=\\)$)");
        
        Matcher patternMatch = pattern.matcher(testString);
        Matcher nameMatch = name.matcher(testString);
        Matcher numberMatch = number.matcher(testString);
        
        if(patternMatch.matches()) {
            result[0] = true;
            nameMatch.find();
            result[1] = nameMatch.group();
            numberMatch.find();
            result[2] = Integer.parseInt(numberMatch.group());
        }
        
        return result; 
    }        
}