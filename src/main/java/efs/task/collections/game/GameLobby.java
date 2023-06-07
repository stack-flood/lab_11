package efs.task.collections.game;

import efs.task.collections.data.DataProvider;
import efs.task.collections.entity.Hero;
import efs.task.collections.entity.Town;

import java.lang.reflect.Array;
import java.util.*;

public class GameLobby {

    public static final String HERO_NOT_FOUND = "Nie ma takiego bohatera ";
    public static final String NO_SUCH_TOWN = "Nie ma takiego miasta ";

    private final DataProvider dataProvider;
    private Map<Town, List<Hero>> playableTownsWithHeroesList;

    public GameLobby() {
        this.dataProvider = new DataProvider();
        this.playableTownsWithHeroesList = mapHeroesToStartingTowns(dataProvider.getTownsList(), dataProvider.getHeroesSet());
    }

    public Map<Town, List<Hero>> getPlayableTownsWithHeroesList() {
        return playableTownsWithHeroesList;
    }

    //TODO Dodać miasta i odpowiadających im bohaterów z DLC gry do mapy dostępnych
    // miast - playableTownsWithHeroesList, tylko jeżeli jeszcze się na niej nie znajdują.
    public void enableDLC() {
        for(Town t : dataProvider.getDLCTownsList()){
            if(!playableTownsWithHeroesList.containsKey(t)){
                playableTownsWithHeroesList.put(t, new ArrayList<Hero>());
            }
        }
        for(Hero hero : dataProvider.getDLCHeroesSet()){
            for(Town town : playableTownsWithHeroesList.keySet()){
                if(town.getStartingHeroClasses().contains(hero.getHeroClass())){
                    List<Hero> current = playableTownsWithHeroesList.get(town);
                    current.add(hero);
                }
            }
        }
    }

    //TODO Usunąć miasta i odpowiadających im bohaterów z DLC gry z mapy dostępnych
    // miast - playableTownsWithHeroesList.
    public void disableDLC() {
        List<Town> listOfTowns = dataProvider.getDLCTownsList();
        for (Town t : listOfTowns) {
            playableTownsWithHeroesList.remove(t);
        }
    }

    // TODO Sprawdza czy mapa playableCharactersByTown zawiera dane miasto.
    //  Jeśli tak zwróć listę bohaterów z tego miasta.
    //  Jeśli nie rzuć wyjątek NoSuchElementException z wiadomością NO_SUCH_TOWN + town.getName()
    public List<Hero> getHeroesFromTown(Town town) {
        if (playableTownsWithHeroesList.containsKey(town)) {
            return playableTownsWithHeroesList.get(town);
        }
        throw new NoSuchElementException(NO_SUCH_TOWN + town.getTownName());
    }

    private static class TownNameComparator implements Comparator<Town>{
        @Override
        public int compare(Town o1, Town o2) {
            return o1.getTownName().compareTo(o2.getTownName());
        }
    }
    // TODO Metoda powinna zwracać mapę miast w kolejności alfabetycznej z odpowiadającymi im bohaterami.
    //  Każde z miast charakteryzuje się dwoma klasami bohaterów dostępnymi dla tego miasta - Town.startingHeroClass.
    //  Mapa ma zawierać pare klucz-wartość gdzie klucz: miasto, wartość: lista bohaterów;
    public Map<Town, List<Hero>> mapHeroesToStartingTowns(List<Town> availableTowns, Set<Hero> availableHeroes) {
//        TreeMap<Town,List<Hero>> result = new TreeMap<>(new TownNameComparator());
//        for(Map.Entry<Town, List<Hero>> entry : playableTownsWithHeroesList.entrySet()){
//            result.put(entry.getKey(), entry.getValue());
//        }
//        return result;
        TreeMap<Town, List<Hero>> result = new TreeMap<>(new TownNameComparator());
        for (Town t : availableTowns) {
            ArrayList<Hero> heroes = new ArrayList<Hero>();
            result.put(t, heroes);
            for(Hero h : availableHeroes){
                if(t.getStartingHeroClasses().contains(h.getHeroClass())){
                    heroes.add(h);
                }
            }
        }
        return result;
    }

    //TODO metoda zwraca wybranego bohatera na podstawie miasta z którego pochodzi i imienia.
    // Jeżeli istnieje usuwa go z listy dostępnych bohaterów w danym mieście i zwraca bohatera.
    // Jeżeli nie ma go na liście dostępnych bohaterów rzuca NoSuchElementException z wiadomością HERO_NOT_FOUND + name
    public Hero selectHeroByName(Town heroTown, String name) {
        if(playableTownsWithHeroesList.containsKey(heroTown)){
            List<Hero> heroes = playableTownsWithHeroesList.get(heroTown);
            for(Hero h : heroes){
                if(h.getName().equals(name)){
                    heroes.remove(h);
                    return h;
                }
            }
        }
        throw new NoSuchElementException(HERO_NOT_FOUND+name);
    }
}
