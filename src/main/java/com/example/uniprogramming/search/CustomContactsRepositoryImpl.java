package com.example.uniprogramming.search;

import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.models.Contact;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomContactsRepositoryImpl implements CustomContactsRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Contact> getFiltered(long userId, long companyId, String surname) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);
        Root<Contact> contact = cq.from(Contact.class);
        Predicate userPred = cb.isTrue(cb.literal(true));
        Predicate companyPred = cb.isTrue(cb.literal(true));
        Predicate surnamePred = cb.isTrue(cb.literal(true));

        if(userId >= 0) userPred = cb.equal(contact.get("user").get("id"), userId);
        if(companyId >= 0) companyPred = cb.equal(contact.get("company").get("id"), companyId);
        if(surname.length() > 0) surnamePred = cb.like(contact.get("surname"), "%" + surname + "%");
        Predicate deleted = cb.isFalse(contact.get("isDeleted"));

        Predicate finalPredicate = cb.and(userPred, companyPred, surnamePred, deleted);

        cq.where(finalPredicate);

        TypedQuery<Contact> query = em.createQuery(cq);

        return query.getResultList();

    }
}
