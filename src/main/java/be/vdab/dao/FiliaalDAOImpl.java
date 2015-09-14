package be.vdab.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import be.vdab.entities.Filiaal;
import be.vdab.valueobjects.PostcodeReeks;

@Repository
class FiliaalDAOImpl implements FiliaalDAO {
	private EntityManager entityManager;

	@PersistenceContext
	void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void create(Filiaal filiaal) {
		entityManager.persist(filiaal);
	}

	@Override
	public Filiaal read(long id) {
		return entityManager.find(Filiaal.class, id);
	}

	@Override
	public void update(Filiaal filiaal) {
		entityManager.merge(filiaal);
	}

	@Override
	public void delete(long id) {
		entityManager.remove(read(id));
	}

	@Override
	public List<Filiaal> findAll() {
		return entityManager.createNamedQuery("Filiaal.findAll", Filiaal.class).getResultList();
	}

	@Override
	public List<Filiaal> findByPostcodeReeks(PostcodeReeks reeks) {
		return entityManager.createNamedQuery("Filiaal.findByPostcodeReeks", Filiaal.class)
				.setParameter("van", reeks.getVanpostcode()).setParameter("tot", reeks.getTotpostcode())
				.getResultList();
	}

	@Override
	public long findAantalFilialen() {
		return entityManager.createNamedQuery("Filiaal.findAantal", Number.class).getSingleResult().longValue();
	}

}

// private final Map<Long, Filiaal> filialen = new ConcurrentHashMap<>();
/*
 * FiliaalDAOImpl() { filialen.put(1L, new Filiaal(1, "Andros", true,
 * BigDecimal.valueOf(1000), new Date(), new Adres("Keizerslaan", "11", 1000,
 * "Brussel"))); filialen.put(2L, new Filiaal(2, "Delos", false,
 * BigDecimal.valueOf(2000), new Date(), new Adres("Gasthuisstraat", "31", 1000,
 * "Brussel"))); filialen.put(3L, new Filiaal(3, "Gavdos", false,
 * BigDecimal.valueOf(3000), new Date(), new Adres("Koestraat", "44", 9700,
 * "Oudenaarde"))); }
 */

/*
 * @Override public void create(Filiaal filiaal) {
 * filiaal.setId(Collections.max(filialen.keySet()) + 1);
 * filialen.put(filiaal.getId(), filiaal); }
 */

/*
 * @Override public Filiaal read(long id) { return filialen.get(id); }
 */

/*
 * @Override public List<Filiaal> findAll() { return new
 * ArrayList<>(filialen.values()); }
 */

/*
 * @Override public long findAantalFilialen() { return filialen.size(); }
 */

/*
 * @Override public long findAantalWerknemers(long id) { return id == 1L ? 7L :
 * 0L; }
 */

// findByPostcodeReeks methods
/*
 * values from map
 * 
 * @Override public List<Filiaal> findByPostcodeReeks(PostcodeReeks reeks) {
 * List<Filiaal> filialen = new ArrayList<>(); for (Filiaal filiaal :
 * this.filialen.values()) { if (reeks.bevat(filiaal.getAdres().getPostcode()))
 * { filialen.add(filiaal); } } return filialen; }
 */