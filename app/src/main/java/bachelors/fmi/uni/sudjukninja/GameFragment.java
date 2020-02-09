package bachelors.fmi.uni.sudjukninja;


import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {


    public GameFragment() {   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Display display =
                getActivity().
                        getWindowManager().
                        getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        GameView gameView =
                new GameView(getActivity(), size.x, size.y);

        return gameView;
   }

}
