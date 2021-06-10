package com.AWT;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

public class Main {

    static void SavePreferences() throws IOException, BackingStoreException {
        Preferences root = Preferences.userRoot(); // наш Преференс
        Preferences systemRoot = Preferences.systemRoot();
        root.put("db.host","http://localhost:8888/mydb"); // сохраняем его значения
        root.put("db.login","root");
        root.put("db.password","dbroot");

        // что мы сохраняем, объяснение
        System.out.println("Save: "+root.get("db.host", "NON")+" "+root.get("db.login", "NON")+" "+root.get("db.password", "NON"));

        Preferences node = root.node("myPack");
        Preferences myPack = Preferences.userNodeForPackage(Preferences.class);

        // сохранение в файл
        root.exportSubtree(new FileOutputStream("myProp.xml"));
    }

    public static void main(String[] args) throws IOException, BackingStoreException {
        SavePreferences(); // наше сохранение
        System.out.println("-----");
        LoadPreferences(); // наша загрузка
    }

    static void LoadPreferences () throws IOException, BackingStoreException{
        // поток из файла
        FileInputStream fis;
        Preferences root = Preferences.userRoot(); // новый Преференс

        try {
            fis = new FileInputStream("myProp.xml"); // файл с данными
            root.importPreferences(fis);// оправляем его значения в Преференс

            String host = (String) root.get("db.host", "Non"); // выгружаем значения в переменные
            String login = (String) root.get("db.login", "Non");
            String password = (String) root.get("db.password", "Non");

            // проверяем, что всё работает
            System.out.println("HOST: " + host
                    + ", LOGIN: " + login
                    + ", PASSWORD: " + password);

        } catch (IOException | InvalidPreferencesFormatException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!"); // ошибка
        }
    }
}
