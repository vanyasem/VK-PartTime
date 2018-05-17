package ru.semkin.ivan.parttime.ui.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ru.semkin.ivan.parttime.data.PostRepository;
import ru.semkin.ivan.parttime.model.Post;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class PostViewModel extends AndroidViewModel {

    private final PostRepository mRepository;
    private final LiveData<List<Post>> mAllPosts;

    public PostViewModel(Application application) {
        super(application);
        mRepository = new PostRepository(application);
        mAllPosts = mRepository.getAllPosts();
    }

    public LiveData<List<Post>> getAllPosts() { return mAllPosts; }
}
