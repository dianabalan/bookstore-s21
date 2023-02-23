package learning.enums;

import java.sql.SQLOutput;

public class Main {

    public static void main(String[] args) {

        Language english = Language.ENGLISH;

        System.out.println(english.getCode() + ":" + english.getLabel());

        for (Language language : Language.values()){
            System.out.println(language.getCode());
        }

        MyClass.INSTANCE.add();

        System.out.println(Language.valueOf("SPANISH").getLabel());
    }
}
