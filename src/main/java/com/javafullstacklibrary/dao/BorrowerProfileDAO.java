package com.javafullstacklibrary.dao;

import com.javafullstacklibrary.model.BorrowerProfile;
import com.javafullstacklibrary.model.LibraryUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class BorrowerProfileDAO {
    
    private EntityManager entityManager;
    
    public BorrowerProfileDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public BorrowerProfile save(BorrowerProfile profile) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            BorrowerProfile result;
            if (profile.getId() == null) {
                entityManager.persist(profile);
                result = profile;
            } else {
                result = entityManager.merge(profile);
            }
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    
    public Optional<BorrowerProfile> findById(Integer id) {
        BorrowerProfile profile = entityManager.find(BorrowerProfile.class, id);
        return Optional.ofNullable(profile);
    }
    
    public List<BorrowerProfile> findAll() {
        TypedQuery<BorrowerProfile> query = entityManager.createQuery(
            "SELECT bp FROM BorrowerProfile bp", BorrowerProfile.class);
        return query.getResultList();
    }
    
    public Optional<BorrowerProfile> findByLibraryUser(LibraryUser libraryUser) {
        try {
            TypedQuery<BorrowerProfile> query = entityManager.createQuery(
                "SELECT bp FROM BorrowerProfile bp WHERE bp.libraryUser = :libraryUser", 
                BorrowerProfile.class);
            query.setParameter("libraryUser", libraryUser);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    public Optional<BorrowerProfile> findByLibraryUserId(Integer libraryUserId) {
        try {
            TypedQuery<BorrowerProfile> query = entityManager.createQuery(
                "SELECT bp FROM BorrowerProfile bp WHERE bp.libraryUser.id = :libraryUserId", 
                BorrowerProfile.class);
            query.setParameter("libraryUserId", libraryUserId);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    public List<BorrowerProfile> findByProfileType(String profileType) {
        TypedQuery<BorrowerProfile> query = entityManager.createQuery(
            "SELECT bp FROM BorrowerProfile bp WHERE bp.profileType = :profileType", 
            BorrowerProfile.class);
        query.setParameter("profileType", profileType);
        return query.getResultList();
    }
    
    public List<BorrowerProfile> findByFirstName(String firstName) {
        TypedQuery<BorrowerProfile> query = entityManager.createQuery(
            "SELECT bp FROM BorrowerProfile bp WHERE bp.firstName = :firstName", 
            BorrowerProfile.class);
        query.setParameter("firstName", firstName);
        return query.getResultList();
    }
    
    public List<BorrowerProfile> findByLastName(String lastName) {
        TypedQuery<BorrowerProfile> query = entityManager.createQuery(
            "SELECT bp FROM BorrowerProfile bp WHERE bp.lastName = :lastName", 
            BorrowerProfile.class);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }
    
    public List<BorrowerProfile> findByFullName(String firstName, String lastName) {
        TypedQuery<BorrowerProfile> query = entityManager.createQuery(
            "SELECT bp FROM BorrowerProfile bp WHERE bp.firstName = :firstName AND bp.lastName = :lastName", 
            BorrowerProfile.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }
    
    public List<BorrowerProfile> findByPhone(String phone) {
        TypedQuery<BorrowerProfile> query = entityManager.createQuery(
            "SELECT bp FROM BorrowerProfile bp WHERE bp.phone = :phone", 
            BorrowerProfile.class);
        query.setParameter("phone", phone);
        return query.getResultList();
    }
    
    public List<BorrowerProfile> findByAddress(String address) {
        TypedQuery<BorrowerProfile> query = entityManager.createQuery(
            "SELECT bp FROM BorrowerProfile bp WHERE bp.address LIKE :address", 
            BorrowerProfile.class);
        query.setParameter("address", "%" + address + "%");
        return query.getResultList();
    }
    
    public List<BorrowerProfile> findPublicUsers() {
        return findByProfileType("public");
    }
    
    public List<BorrowerProfile> findStudents() {
        return findByProfileType("student");
    }
    
    public List<BorrowerProfile> findResearchers() {
        return findByProfileType("researcher");
    }
    
    public List<BorrowerProfile> findUniversityEmployees() {
        return findByProfileType("university employee");
    }
    
    public void deleteById(Integer id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            BorrowerProfile profile = entityManager.find(BorrowerProfile.class, id);
            if (profile != null) {
                entityManager.remove(profile);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    
    public void delete(BorrowerProfile profile) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (entityManager.contains(profile)) {
                entityManager.remove(profile);
            } else {
                entityManager.remove(entityManager.merge(profile));
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    
    public boolean existsById(Integer id) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(bp) FROM BorrowerProfile bp WHERE bp.id = :id", Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }
    
    public boolean existsByLibraryUserId(Integer libraryUserId) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(bp) FROM BorrowerProfile bp WHERE bp.libraryUser.id = :libraryUserId", 
            Long.class);
        query.setParameter("libraryUserId", libraryUserId);
        return query.getSingleResult() > 0;
    }
    
    public boolean existsByPhone(String phone) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(bp) FROM BorrowerProfile bp WHERE bp.phone = :phone", Long.class);
        query.setParameter("phone", phone);
        return query.getSingleResult() > 0;
    }
    
    public long countByProfileType(String profileType) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(bp) FROM BorrowerProfile bp WHERE bp.profileType = :profileType", 
            Long.class);
        query.setParameter("profileType", profileType);
        return query.getSingleResult();
    }
    
    public long countAll() {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(bp) FROM BorrowerProfile bp", Long.class);
        return query.getSingleResult();
    }
}