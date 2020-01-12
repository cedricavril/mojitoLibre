package fr.avril.cedric.mojitolibre;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Page1Fragment extends FragmentBase {

    /**
     * création de la vue du fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_page1, container, false);
    }

    /**
     * après création vue
     * installation listener bouton
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Button btnPage1 = (Button) view.findViewById(R.id.btnPage1);
        btnPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animAlphaFragment(false, dureeAnimFragment);
                actionUlterieure("PageMenu", dureeAnimFragment);
            }
        });
    }

    /**
     * fin du délai initié par FragmentBase::actionUlterieure()
     */
    @Override
    protected void callbackActionUlterieure(String action)
    {
        if (action.equals("PageMenu")) {
            mainActivity.afficherPage(2);
        }
    }
}
