import javafx.beans.property.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by SBT-Vdovin-AI on 10.07.2017.
 */
public class Person2 implements Serializable{
    private ObjectProperty<LocalDate> CREATING_DATE;
    private transient StringProperty json;
    private IntegerProperty followedBy;
    private IntegerProperty follows;
    private IntegerProperty posts;
    private LongProperty id;
    private StringProperty userName;
    private StringProperty fullName;
    private StringProperty biography;
    private BooleanProperty isPrivate;
    private BooleanProperty isVerified;
    public BooleanProperty isExist;

    public LocalDate getCREATING_DATE() {
        return CREATING_DATE.get();
    }

    public ObjectProperty<LocalDate> CREATING_DATEProperty() {
        return CREATING_DATE;
    }

    public void setCREATING_DATE(LocalDate CREATING_DATE) {
        this.CREATING_DATE.set(CREATING_DATE);
    }

    public String getJson() {
        return json.get();
    }

    public StringProperty jsonProperty() {
        return json;
    }

    public void setJson(String json) {
        this.json.set(json);
    }

    public int getFollowedBy() {
        return followedBy.get();
    }

    public IntegerProperty followedByProperty() {
        return followedBy;
    }

    public void setFollowedBy(int followedBy) {
        this.followedBy.set(followedBy);
    }

    public int getFollows() {
        return follows.get();
    }

    public IntegerProperty followsProperty() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows.set(follows);
    }

    public int getPosts() {
        return posts.get();
    }

    public IntegerProperty postsProperty() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts.set(posts);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getFullName() {
        return fullName.get();
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public String getBiography() {
        return biography.get();
    }

    public StringProperty biographyProperty() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography.set(biography);
    }

    public boolean isIsPrivate() {
        return isPrivate.get();
    }

    public BooleanProperty isPrivateProperty() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate.set(isPrivate);
    }

    public boolean isIsVerified() {
        return isVerified.get();
    }

    public BooleanProperty isVerifiedProperty() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified.set(isVerified);
    }

    public boolean isIsExist() {
        return isExist.get();
    }

    public BooleanProperty isExistProperty() {
        return isExist;
    }

    public void setIsExist(boolean isExist) {
        this.isExist.set(isExist);
    }
}