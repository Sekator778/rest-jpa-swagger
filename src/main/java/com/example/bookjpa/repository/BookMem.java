//package com.example.bookjpa.repository;
//
//import com.example.bookjpa.domain.BookEntity;
//import org.springframework.stereotype.Repository;
//import ru.job4j.accident.model.Accident;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * repository all accident
// */
//@Repository
//public class AccidentMem {
//    private final HashMap<Integer, BookEntity> accidents;
//    private static final AtomicInteger ACCIDENT_ID = new AtomicInteger(4);
//
//
//    public AccidentMem() {
//        accidents = new HashMap<Integer, Accident>();
//        init();
//    }
//
//    /**
//     * Method add new accident
//     *
//     * @param accident - new accident
//     */
//    public void addAccident(Accident accident) {
//        if (accident.getId() == 0) {
//            accident.setId(ACCIDENT_ID.incrementAndGet());
//        }
//        accidents.put(accident.getId(), accident);
//    }
//
//    /**
//     * remove accident
//     */
//    public boolean removeAccident(Accident accident) {
//        return accidents.remove(accident) != null;
//    }
//
//    /**
//     * method for fill in store
//     */
//    private void init() {
////        Accident accident1 = new Accident(1, "Bob", "some accident speed over limit", "Kytyzova 15");
////        Accident accident2 = new Accident(2, "Garson", "easy accident cross two white lane", "Korolyova 26");
////        Accident accident3 = new Accident(3, "Karlson", "hard accident riding without a belt", "Koshkina 7");
////        addAccident(accident1);
////        addAccident(accident2);
////        addAccident(accident3);
//    }
//
//    /**
//     * @return list all accidents
//     */
//    public List<Accident> findAll() {
//        return new ArrayList<Accident>(accidents.values());
//    }
//}
