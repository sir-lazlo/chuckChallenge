package chuck.com.challenge.activities.infiniteListActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import chuck.com.challenge.R;
import chuck.com.challenge.Presenters.BatchJokePresenter;
import chuck.com.challenge.activities.baseActivity.BaseFragment;
import chuck.com.challenge.adapters.JokeListAdapter;
import chuck.com.challenge.appEnums.ContentValuesEnum;
import chuck.com.challenge.appListeners.IBatchJokeView;
import chuck.com.challenge.appListeners.InfiniteListListener;
import chuck.com.challenge.helpers.DialogHelper;
import chuck.com.challenge.responsePojo.JokeEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfiniteListFragment extends BaseFragment implements
        IBatchJokeView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    JokeListAdapter jokeListAdapter;

    BatchJokePresenter batchJokePresenter;

    private List<JokeEntry> jokes = new ArrayList<>();

    public InfiniteListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_infinite_list,
                container, false);
        ButterKnife.bind(this, view);
        batchJokePresenter = new BatchJokePresenter(this);
        initUI();
        return view;
    }

    private void initUI() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        jokeListAdapter = new JokeListAdapter(getActivity());
        recyclerView.setAdapter(jokeListAdapter);

        batchJokePresenter.fetchBatchOfRandomJokes();

        recyclerView.addOnScrollListener(new InfiniteListListener() {
            @Override
            public void onLoadMore() {
                batchJokePresenter.fetchBatchOfRandomJokes();
            }
        });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null
                && savedInstanceState
                        .containsKey(ContentValuesEnum.RECYCLER_VIEW.getKey())
                && savedInstanceState
                        .containsKey(ContentValuesEnum.RECYCLER_VIEW_DATA
                                .getKey())) {

            jokes = (List<JokeEntry>) savedInstanceState
                    .getSerializable(ContentValuesEnum.RECYCLER_VIEW_DATA
                            .getKey());

            ((JokeListAdapter) recyclerView.getAdapter()).setItems(jokes);

            recyclerView.getLayoutManager().onRestoreInstanceState(
                    savedInstanceState
                            .getParcelable(ContentValuesEnum.RECYCLER_VIEW
                                    .getKey()));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ContentValuesEnum.RECYCLER_VIEW.getKey(),
                recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putSerializable(ContentValuesEnum.RECYCLER_VIEW_DATA.getKey(),
                (Serializable) jokes);
    }

    @Override
    public void onJokesLoaded(List<JokeEntry> jokeEntries) {
        jokes.addAll(jokeEntries);
        jokeListAdapter.setItems(jokes);
        jokeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message) {
        DialogHelper.getErrorDialog(getActivity(), message).show();
    }
}
