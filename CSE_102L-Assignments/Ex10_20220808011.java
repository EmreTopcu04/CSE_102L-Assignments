import java.util.*;
public class Ex10_20220808011 {
    public static void main(String[] args) {

    }
}

class User {
    private int id;
    private String username;
    private String email;
    private HashSet<User> followers;
    private HashSet<User> following;
    private HashSet<Post> likedPosts;
    private HashMap<User, Queue<Message>> messages;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.id = this.hashCode();
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
        this.likedPosts = new HashSet<>();
        this.messages = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashSet<User> getFollowers() {
        return followers;
    }

    public HashSet<User> getFollowing() {
        return following;
    }

    public HashSet<Post> getLikedPosts() {
        return likedPosts;
    }

    public void message(User recipient, String content) {
        Queue<Message> message_1 = messages.getOrDefault(recipient,new LinkedList<>());
        Queue<Message> message_2 = recipient.messages.getOrDefault(recipient,new LinkedList<>());
        Message message = new Message(this,content);
        message_1.add(message);
        message_2.add(message);
        messages.put(recipient,message_1);
        recipient.messages.put(this,message_2);
        read(recipient);
    }

    public void read(User user) {
        Queue<Message> userMessages = messages.get(user);
            for (Message message : userMessages) {
                message.read(user);
            }
        }
    public void follow(User user) {
        if (following.contains(user)) {
            following.remove(user);
            user.followers.remove(this);
        } else {
            following.add(user);
            user.followers.add(this);
        }
    }
    public void like(Post post) {
        if (likedPosts.contains(post)) {
            likedPosts.remove(post);
            post.likedBy(this);
        } else {
            likedPosts.add(post);
            post.likedBy(this);
        }
    }
    public Post post(String content) {
        return new Post(content);
    }

    public Comment comment(Post post, String content) {
        Comment newComment = new Comment(content);
        post.commentBy(this, newComment);
        return newComment;
    }
    @Override
    public boolean equals(Object o){
        if (o.getClass() == this.getClass()){
            User user = (User) o;
            return this.id == user.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}

class Message {
    private boolean seen;
    private Date dateSent;
    private String content;
    private User sender;

    Message(User sender, String content) {
        this.sender = sender;
        this.content = content;
        this.dateSent = new Date();
        this.seen = false;
    }

    public void read(User reader) {
        if (!sender.equals(reader)) {
            seen = true;
        }
        System.out.println("Sent at: " + dateSent);
        System.out.println("Content: " + content);
    }

    public boolean hasRead() {
        return seen;
    }
}


class Post {
    private java.util.Date datePosted;
    private String content;
    private HashSet<User> likes;
    private HashMap<User, ArrayList<Comment>> comments;

    Post(String content) {
        this.content = content;
        Date d1 = new Date();
        datePosted = d1;
        likes = new HashSet<>();
        comments = new HashMap<>();
    }

    public boolean likedBy(User user) {
        if (likes.contains(user)) {
            likes.remove(user);
            return false;
        } else {
            likes.add(user);
            return true;
        }
    }

    public boolean commentBy(User user, Comment comment) {
        comments.putIfAbsent(user, new ArrayList<>());
        return comments.get(user).add(comment);
    }

    public String getContent() {
        System.out.println("Posted at: " + datePosted);
        return content;
    }

    public Comment getComment(User user, int index) {
        if (comments.containsKey(user) && index >= 0 && index < comments.get(user).size()) {
            return comments.get(user).get(index);
        }
        return null;
    }

    public int getCommentCount() {
        int count = 0;
        for (User users : comments.keySet()) {
            if (comments.get(users) != null) {
                count += comments.get(users).size();
            }
        }
        return count;
    }

    public int getCommentCountByUser(User user) {
        ArrayList<Comment> commentedByUser = comments.get(user);
        if (commentedByUser != null) {
            return commentedByUser.size();
        } else {
            return 0;
        }
    }
}

class Comment extends Post {
    Comment(String content) {
        super(content);
    }
}

class SocialNetwork {
    private static Map<User, List<Post>> postsByUsers= new HashMap<>();

    public static User register(String username, String email) {
        User newUser = new User(username, email);
        if (!postsByUsers.containsKey(newUser)) {
            postsByUsers.put(newUser, new ArrayList<>());
            return newUser;
        }
        return null;
    }

    public static Post post(User user, String content) {
        if (postsByUsers.containsKey(user)) {
            Post newPost = new Post(content);
            postsByUsers.get(user).add(newPost);
            return newPost;
        }
        return null;
    }

    public static User getUser(String email) {
        int hashedEmail = (Objects.hash(email));
        for (User user : postsByUsers.keySet()) {
            if (user.hashCode() == hashedEmail) {
                return user;
            }
        }
        return null;
    }

    public static Set<Post> getFeed(User user) {
        HashSet<Post> feed = new HashSet<>();
        for (User followingUser : user.getFollowing()) {
            feed.addAll(postsByUsers.getOrDefault(followingUser, Collections.emptyList()));
        }
        return feed;
    }

    public static Map<User, String> search(String keyword) {
        HashMap<User, String> searchResults = new HashMap<>();
        for (User user : postsByUsers.keySet()) {
            if (user.getUsername().contains(keyword)) {
                searchResults.put(user, user.getUsername());
            }
        }
        return searchResults;
    }

    public static <K, V> Map<V, Set<K>> reverseMap(Map<K, V> map) {
        Map<V, Set<K>> reversedMap = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            reversedMap.putIfAbsent(value, new HashSet<>());
            reversedMap.get(value).add(key);
        }
        return reversedMap;
    }
}

