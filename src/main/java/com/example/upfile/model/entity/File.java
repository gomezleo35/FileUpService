package com.example.upfile.model.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;


    @Column(name = "hash_sha_256")
    private String hash256;

    @Column(name = "hash_sha_512")
    private String hash512;

    @Column(name = "fileName")
    private String fileName;

    private Date lastUpload;
}
