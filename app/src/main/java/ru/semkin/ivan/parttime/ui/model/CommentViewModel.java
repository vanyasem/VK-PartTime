package ru.semkin.ivan.parttime.ui.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ru.semkin.ivan.parttime.data.CommentRepository;
import ru.semkin.ivan.parttime.model.Comment;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class CommentViewModel extends AndroidViewModel {

    private final CommentRepository mRepository;
    private final LiveData<List<Comment>> mAllComments;

    public CommentViewModel(Application application) {
        super(application);
        mRepository = new CommentRepository(application);
        mAllComments = mRepository.getAllComments();
    }

    public LiveData<List<Comment>> getAllComments() { return mAllComments; }
}
