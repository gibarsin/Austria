package ar.com.nameless.interfaces.service;

import ar.com.nameless.model.Flag;
import ar.com.nameless.model.User;

/**
 * Created by root on 2/25/17.
 */
public interface InteractionService {
    boolean likePost(User user, long id);
    boolean dislikePost(User user, long id);
    boolean flagPost(User user, long id, Flag.Category category);
}
