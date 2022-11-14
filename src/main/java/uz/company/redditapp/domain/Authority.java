package uz.company.redditapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
@Getter
@EqualsAndHashCode
public class Authority implements Serializable {

    private static final long serialVersionUID = 6L;
    @NotNull
    @Size(max = 50)
    @Id
    @Column(name = "name")
    private String name;
}
