package net.indrix.maps.dao;

// Generated 16/09/2006 18:01:29 by Hibernate Tools 3.1.0.beta5

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class CityDescription.
 * @see net.indrix.maps.dao.CityDescription
 * @author Hibernate Tools
 */
public class CityDescriptionHome {

    private static final Log log = LogFactory.getLog(CityDescriptionHome.class);

    private final SessionFactory sessionFactory = getSessionFactory();

    protected SessionFactory getSessionFactory() {
        try {
            return (SessionFactory) new InitialContext()
                    .lookup("SessionFactory");
        } catch (Exception e) {
            log.error("Could not locate SessionFactory in JNDI", e);
            throw new IllegalStateException(
                    "Could not locate SessionFactory in JNDI");
        }
    }

    public void persist(CityDescription transientInstance) {
        log.debug("persisting CityDescription instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(CityDescription instance) {
        log.debug("attaching dirty CityDescription instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CityDescription instance) {
        log.debug("attaching clean CityDescription instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void delete(CityDescription persistentInstance) {
        log.debug("deleting CityDescription instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CityDescription merge(CityDescription detachedInstance) {
        log.debug("merging CityDescription instance");
        try {
            CityDescription result = (CityDescription) sessionFactory
                    .getCurrentSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public CityDescription findById(java.lang.String id) {
        log.debug("getting CityDescription instance with id: " + id);
        try {
            CityDescription instance = (CityDescription) sessionFactory
                    .getCurrentSession().get(
                            "net.indrix.maps.dao.CityDescription", id);
            if (instance == null) {
                log.debug("get successful, no instance found");
            } else {
                log.debug("get successful, instance found");
            }
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CityDescription instance) {
        log.debug("finding CityDescription instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria(
                    "net.indrix.maps.dao.CityDescription").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }
}
