package chuck.com.challenge.activities.launchActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import chuck.com.challenge.R;
import chuck.com.challenge.Presenters.SingleJokePresenter;
import chuck.com.challenge.activities.baseActivity.BaseFragment;
import chuck.com.challenge.appListeners.ISingleJokeView;
import chuck.com.challenge.helpers.DialogHelper;

public class SingleJokeFragment extends BaseFragment implements ISingleJokeView {

    @BindView(R.id.titleSwitcher)
    TextSwitcher titleSwitcher;

    @BindView(R.id.jokeSwitcher)
    TextSwitcher jokeSwitcher;

    private SingleJokePresenter randomSingleJokePresenter;

    private ViewSwitcher.ViewFactory titleFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {
            TextView t = new TextView(getActivity());
            t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                t.setTextAppearance(R.style.Content_Large);
            } else {
                t.setTextAppearance(getActivity(), R.style.Content);
            }

            return t;
        }
    };

    private ViewSwitcher.ViewFactory jokeFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {
            TextView t = new TextView(getActivity());
            t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                t.setTextAppearance(R.style.Content_Large_Dark);
            } else {
                t.setTextAppearance(getActivity(), R.style.Content_Dark);
            }
            return t;
        }
    };

    public SingleJokeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_launch, container, false);
        ButterKnife.bind(this, view);
        randomSingleJokePresenter = new SingleJokePresenter(this);
        init();
        return view;
    }

    private void init() {
        titleSwitcher.setFactory(titleFactory);
        titleSwitcher.setAnimateFirstView(false);
        titleSwitcher.setOutAnimation(getContext(), R.anim.slide_right_to_left);
        titleSwitcher.setInAnimation(getContext(), R.anim.slide_left_to_right);

        jokeSwitcher.setFactory(jokeFactory);
        jokeSwitcher.setAnimateFirstView(false);
        jokeSwitcher.setOutAnimation(getContext(), R.anim.slide_right_to_left);
        jokeSwitcher.setInAnimation(getContext(), R.anim.slide_left_to_right);

        loadJoke();
    }

    public void loadJoke() {
        randomSingleJokePresenter.fetchSingleRandomJoke();
    }

    @Override
    public void onJokeLoaded(SpannableString title, String joke) {
        titleSwitcher.setText(title);
        jokeSwitcher.setText(joke);
    }

    @Override
    public void onError(String message) {
        DialogHelper.getErrorDialog(getActivity(), message).show();
    }

}
