/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Macko
 */
@Entity
@Table(name = "GAME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
    @NamedQuery(name = "Game.findById", query = "SELECT g FROM Game g WHERE g.id = :id"),
    @NamedQuery(name = "Game.findByScore1", query = "SELECT g FROM Game g WHERE g.score1 = :score1"),
    @NamedQuery(name = "Game.findByScore2", query = "SELECT g FROM Game g WHERE g.score2 = :score2")})
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "SCORE1")
    private Integer score1;
    @Column(name = "SCORE2")
    private Integer score2;
    @JoinColumn(name = "TEAMID1", referencedColumnName = "ID")
    @ManyToOne
    private Team teamid1;
    @JoinColumn(name = "TEAMID2", referencedColumnName = "ID")
    @ManyToOne
    private Team teamid2;

    public Game() {
    }

    public Game(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public Team getTeamid1() {
        return teamid1;
    }

    public void setTeamid1(Team teamid1) {
        this.teamid1 = teamid1;
    }

    public Team getTeamid2() {
        return teamid2;
    }

    public void setTeamid2(Team teamid2) {
        this.teamid2 = teamid2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pb.Game[ id=" + id + " ]";
    }
    
}
