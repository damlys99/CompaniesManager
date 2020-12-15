package com.example.uniprogramming.search;

import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.search.CustomCompaniesRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomCompaniesRepositoryImpl implements CustomCompaniesRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Company> getFiltered(List<String> industries, List<String> dates, Pageable pageable) {
        List<Predicate> industriesPredicates = new ArrayList<>();
        List<Predicate> datesPredicates = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Company> cq = cb.createQuery(Company.class);
        Root<Company> company = cq.from(Company.class);
        Predicate industriesFinal = cb.isTrue(cb.literal(true));
        Predicate datesFinal = cb.isTrue(cb.literal(true));
        if(industries.size() > 0) {
            for (String industry : industries) {
                Predicate pred = cb.equal(company.get("industry").get("name"), industry);
                industriesPredicates.add(pred);
            }
            industriesFinal = cb.or(industriesPredicates.toArray(new Predicate[0]));
        }
        if(dates.size() > 0) {
            for (String date : dates) {
                Predicate pred = cb.equal(company.get("added"), Date.valueOf(date));
                datesPredicates.add(pred);
            }
            datesFinal = cb.or(datesPredicates.toArray(new Predicate[0]));
        }

        Predicate deleted = cb.isFalse(company.get("isDeleted"));
        Predicate finalPredicate = cb.and(deleted,industriesFinal,datesFinal);

        cq.where(finalPredicate);
        TypedQuery<Company> query = em.createQuery(cq);


        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();

    }

    @Override
    public Long getCount(List<String> industries, List<String> dates) {
        List<Predicate> industriesPredicates = new ArrayList<>();
        List<Predicate> datesPredicates = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Company> company = cq.from(Company.class);
        Predicate industriesFinal = cb.isTrue(cb.literal(true));
        Predicate datesFinal = cb.isTrue(cb.literal(true));
        if (industries.size() > 0) {
            for (String industry : industries) {
                Predicate pred = cb.equal(company.get("industry").get("name"), industry);
                industriesPredicates.add(pred);
            }
            industriesFinal = cb.or(industriesPredicates.toArray(new Predicate[0]));
        }
        if (dates.size() > 0) {
            for (String date : dates) {
                Predicate pred = cb.equal(company.get("added"), Date.valueOf(date));
                datesPredicates.add(pred);
            }
            datesFinal = cb.or(datesPredicates.toArray(new Predicate[0]));
        }

        Predicate deleted = cb.isFalse(company.get("isDeleted"));
        Predicate finalPredicate = cb.and(deleted, industriesFinal, datesFinal);



        return em.createQuery(cq.select(cb.count(company)).where(finalPredicate)).getSingleResult();


    }
}
