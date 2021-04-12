package tech.pzjswpooks.zzpj.chat.api.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Collection;

@Entity
@Table(name = "accounts", schema = "public", catalog = "chatdb", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"user_id"})})
public class AccountsEntity {
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private Long version;
    private Long userId;
    private Collection<AccessLevelsEntity> accessLevelsById;
    private UsersEntity usersByUserId;
    private Collection<ChatMessagesEntity> chatMessagesById;
    private Collection<ChatUsersEntity> chatUsersById;
    private Collection<ChatsEntity> chatsById;

    @Id
    @SequenceGenerator(name = "accounts_generator", sequenceName = "accounts_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_generator")
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 32)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic(optional = false)
    @Column(name = "enabled", nullable = false)
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AccountsEntity that = (AccountsEntity) o;

        return new EqualsBuilder().append(id, that.id).append(username, that.username).append(password, that.password).append(enabled, that.enabled).append(version, that.version).append(userId, that.userId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(username).append(password).append(enabled).append(version).append(userId).toHashCode();
    }

    @OneToMany(mappedBy = "accountsByAccountId")
    public Collection<AccessLevelsEntity> getAccessLevelsById() {
        return accessLevelsById;
    }

    public void setAccessLevelsById(Collection<AccessLevelsEntity> accessLevelsById) {
        this.accessLevelsById = accessLevelsById;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @OneToMany(mappedBy = "accountsByAccountId")
    public Collection<ChatMessagesEntity> getChatMessagesById() {
        return chatMessagesById;
    }

    public void setChatMessagesById(Collection<ChatMessagesEntity> chatMessagesById) {
        this.chatMessagesById = chatMessagesById;
    }

    @OneToMany(mappedBy = "accountsByAccountId")
    public Collection<ChatUsersEntity> getChatUsersById() {
        return chatUsersById;
    }

    public void setChatUsersById(Collection<ChatUsersEntity> chatUsersById) {
        this.chatUsersById = chatUsersById;
    }

    @OneToMany(mappedBy = "accountsByOwnerId")
    public Collection<ChatsEntity> getChatsById() {
        return chatsById;
    }

    public void setChatsById(Collection<ChatsEntity> chatsById) {
        this.chatsById = chatsById;
    }
}