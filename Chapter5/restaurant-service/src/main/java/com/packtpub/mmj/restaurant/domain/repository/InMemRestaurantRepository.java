package com.packtpub.mmj.restaurant.domain.repository;

import com.packtpub.mmj.restaurant.common.RestaurantNotFoundException;
import com.packtpub.mmj.restaurant.domain.model.entity.Restaurant;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * @author Sourabh Sharma
 */
@Repository("restaurantRepository")
public class InMemRestaurantRepository implements RestaurantRepository<Restaurant, String> {

  private static final Map<String, Restaurant> entities;

  /**
   * Initialize the in-memory Restaurant map
   */
  static {
    entities = new ConcurrentHashMap<>(Map.ofEntries(
        new SimpleEntry<>("1",
            new Restaurant("Le Meurice", "1", "228 rue de Rivoli, 75001, Paris", Optional.empty())),
        new SimpleEntry<>("2",
            new Restaurant("L'Ambroisie", "2", "9 place des Vosges, 75004, Paris",
                Optional.empty())),
        new SimpleEntry<>("3",
            new Restaurant("Arpège", "3", "84, rue de Varenne, 75007, Paris", Optional.empty())),
        new SimpleEntry<>("4", new Restaurant("Alain Ducasse au Plaza Athénée", "4",
            "25 avenue de Montaigne, 75008, Paris", Optional.empty())),
        new SimpleEntry<>("5",
            new Restaurant("Pavillon LeDoyen", "5", "1, avenue Dutuit, 75008, Paris",
                Optional.empty())),
        new SimpleEntry<>("6",
            new Restaurant("Pierre Gagnaire", "6", "6, rue Balzac, 75008, Paris",
                Optional.empty())),
        new SimpleEntry<>("7",
            new Restaurant("L'Astrance", "7", "4, rue Beethoven, 75016, Paris", Optional.empty())),
        new SimpleEntry<>("8",
            new Restaurant("Pré Catelan", "8", "Bois de Boulogne, 75016, Paris", Optional.empty())),
        new SimpleEntry<>("9",
            new Restaurant("Guy Savoy", "9", "18 rue Troyon, 75017, Paris", Optional.empty())),
        new SimpleEntry<>("10", new Restaurant("Le Bristol", "10",
            "112, rue du Faubourg St Honoré, 8th arrondissement, Paris", Optional.empty()))));
  }

  /**
   * Check if given restaurant name already exist.
   *
   * @param name
   * @return true if already exist, else false
   */
  @Override
  public boolean containsName(String name) {
    try {
      return !this.findByName(name).isEmpty();
    } catch (RestaurantNotFoundException ex) {
      return false;
    } catch (Exception ex) {
      //Exception Handler
    }
    return false;
  }

  /**
   * @param entity
   */
  @Override
  public void add(Restaurant entity) {
    entities.put(entity.getId(), entity);
  }

  /**
   * @param id
   */
  @Override
  public void remove(String id) {
    if (entities.containsKey(id)) {
      entities.remove(id);
    }
  }

  /**
   * @param entity
   */
  @Override
  public void update(Restaurant entity) {
    if (entities.containsKey(entity.getId())) {
      entities.put(entity.getId(), entity);
    }
  }

  /**
   * @param id
   */
  @Override
  public boolean contains(String id) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * @param id
   */
  @Override
  public Restaurant get(String id) {
    return entities.get(id);
  }

  /**
   *
   * @return
   */
  @Override
  public Collection<Restaurant> getAll() {
    return entities.values();
  }

  /**
   * @param name
   */
  @Override
  public Collection<Restaurant> findByName(String name) throws RestaurantNotFoundException {
    int noOfChars = name.length();
    Collection<Restaurant> restaurants = entities.entrySet().stream()
        .filter(e -> e.getValue().getName().toLowerCase().contains(name.subSequence(0, noOfChars)))
        .collect(Collectors.toList())
        .stream()
        .map(k -> k.getValue())
        .collect(Collectors.toList());
    if (restaurants != null && restaurants.isEmpty()) {
      Object[] args = {name};
      throw new RestaurantNotFoundException("restaurantNotFound", args);
    }
    return restaurants;
  }
}
