package efs.task.collections.data;

import efs.task.collections.entity.Hero;
import efs.task.collections.entity.Town;

import java.lang.reflect.Array;
import java.util.*;

public class DataProvider {

    public static final String DATA_SEPARATOR = ",";

    // TODO Utwórz listę miast na podstawie tablicy Data.baseTownsArray.
    //  Tablica zawiera String zawierający nazwę miasta oraz dwie klasy bohatera związane z tym miastem oddzielone przecinkami.
    //  Korzystając z funkcji split() oraz stałej DATA_SEPARATOR utwórz listę obiektów klasy efs.task.collections.entities.Town.
    //  Funkcja zwraca listę obiektów typu Town ze wszystkimi dziewięcioma podstawowymi miastami.
    public List<Town> getTownsList() {
        ArrayList<Town> towns = new ArrayList<>();
        for(String line : Data.baseTownsArray){
            ArrayList<String> heroes = new ArrayList<>();
            String[] parts = line.split(DATA_SEPARATOR);
            heroes.add(parts[1].trim());
            heroes.add(parts[2].trim());
            Town t = new Town(parts[0], heroes);
            towns.add(t);
        }
        return towns;
    }

    //TODO Analogicznie do getTownsList utwórz listę miast na podstawie tablicy Data.DLCTownsArray
    public List<Town> getDLCTownsList() {
        ArrayList<Town> towns = new ArrayList<>();
        for(String line : Data.dlcTownsArray){
            ArrayList<String> heroes = new ArrayList<>();
            String[] parts = line.split(DATA_SEPARATOR);
            heroes.add(parts[1].trim());
            heroes.add(parts[2].trim());
            Town t = new Town(parts[0], heroes);
            towns.add(t);
        }
        return towns;
    }

    //TODO Na podstawie tablicy Data.baseCharactersArray utworzyć listę bohaterów dostępnych w grze.
    // Tablica Data.baseCharactersArray zawiera oddzielone przecinkiem imie bohatera, klasę bohatera.
    // Korzystając z funkcji split() oraz DATA_SEPARATOR utwórz listę unikalnych obiektów efs.task.collections.entities.Hero.
    // UWAGA w Data.baseCharactersArray niektórzy bohaterowie powtarzają się, do porównania bohaterów używamy zarówno imie jak i jego klasę;
    public Set<Hero> getHeroesSet() {
        Set<Hero> heroes = new HashSet<>();
        for(String line : Data.baseCharactersArray){
            String[] parts = line.split(DATA_SEPARATOR);
            Hero current = new Hero(parts[0], parts[1].trim());
            if(!heroes.contains(current));
            heroes.add(current);
        }
        return heroes;
    }

    //TODO Analogicznie do getHeroesSet utwórz listę bohaterów na podstawie tablicy Data.DLCCharactersArray
    public Set<Hero> getDLCHeroesSet() {
        Set<Hero> heroes = new HashSet<>();
        for(String line : Data.dlcCharactersArray){
            String[] parts = line.split(DATA_SEPARATOR);
            Hero current = new Hero(parts[0], parts[1].trim());
            if(!heroes.contains(current)){
                heroes.add(current);
            }
        }
        return heroes;
    }
}
