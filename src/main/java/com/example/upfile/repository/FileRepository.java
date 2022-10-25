package com.example.upfile.repository;

import com.example.upfile.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
@Transactional
public interface FileRepository extends JpaRepository<File, Integer>{


    Optional<File> findByHash256(String hash);

    Optional<File> findByHash512(String hash);


    @Modifying
    @Query("update File u set u.lastUpload = ?1 where u.id = ?2")
    void updateByid(Date date, Integer id);
}
