package fr.avril.cedric.mojitolibre;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import java.util.ArrayList;

/**
 * classe fragment menu choix recette
 *
 * @todo classe ancêtre des fragments
 */
public class Page2Fragment extends FragmentBase {

    private static final String nomsBtns[] = {      // noms des boutons
            "btnAA",                                // -> sous-menu "AA" : avec alcool
            "btnAA_classic",                        // -> 2 boutons "recette" sous-menu "AA"
            "btnAA_royal",                          //
            "btnSA",                                // -> sous-menu "SA" : sans alcool
            "btnSA_lychee",
            "btnSA_virgin"                          // NB: les noms des boutons "recette" commencent
    };                                              // comme celui du bouton sous-menu correspondant

    final ArrayList<ImageButton>                    // liste boutons pour animation alpha
            btnsSortants = new ArrayList<ImageButton>(0),     // boutons à faire disparaître progressivement
            btnsEntrants = new ArrayList<ImageButton>(0);     // boutons à faire apparaître progressivement

    final int dureeAnimSortieBtns = 200;            // durée animation retrait boutons
    final int dureeAnimEntreeBtns = 500;            // durée animation apparition boutons

    private String sousMenuActif = "";              // nom sous-menu actif ou vide

    /**
     * test si bouton d'ouverture de sous-menu (càd "avec alcool" ou "sans alcool")
     */
    private boolean estBoutonSousMenu(String nomBtn)
    {
        return nomBtn.matches("^btn[SA]A$");
    }

    /**
     * animation canal alpha d'une collection de boutons
     */
    private void animAlphaBtns(ArrayList<ImageButton> listeBtns, boolean apparition, int duree)
    {
        for (ImageButton btn : listeBtns) {
            float alphaInitial = (apparition) ? 0f : 1f;
            float alphaFinal = 1 - alphaInitial;
            AlphaAnimation anim = new AlphaAnimation(alphaInitial, alphaFinal);
            anim.setDuration(duree);
            btn.startAnimation(anim);
        }
    }

    /**
     * configuration sous-menu
     *
     * - animation des apparitions / disparitions de boutons (canal alpha)
     * - masquage des boutons de sous-menu lorsqu'un sous-menu est ouvert
     *
     * NB: implique 2 niveaux d'exploitation de la page 2 (choix sous-menu / choix option)
     * => traiterKeycodeBack() : 1er "back" = fermer sous-menu, 2ème = retour page 1
     *
     * @param nomSousMenu String Nom du sous-menu à afficher ou vide = menu principal
     */
    private void configSousMenu(String nomSousMenu)
    {
        sousMenuActif = nomSousMenu;
        btnsEntrants.clear();
        btnsSortants.clear();                                         // parcours liste boutons
	    for (String nomBtn : nomsBtns) {
            int idBtn = this.getResources().getIdentifier(nomBtn, "id", mainActivity.getPackageName());
            ImageButton btn = viewFragment.findViewById(idBtn);
            boolean vis = (nomSousMenu.equals(""))                    // visibilité bouton
                    ? estBoutonSousMenu(nomBtn)
                    : nomBtn.startsWith(nomSousMenu) && !nomBtn.equals(nomSousMenu);
            if (vis != (btn.getVisibility() == View.VISIBLE))
                if (vis)
                    btnsEntrants.add(btn);                            // changement visibilité :
                else btnsSortants.add(btn);                           // enlistage bouton
        }

        boolean deuxPhases = (btnsSortants.size() > 0);               // au 1er affichage, pas de phase
        if (deuxPhases)                                               // de sortie des boutons
            animAlphaBtns(btnsSortants, false, dureeAnimSortieBtns);
        actionUlterieure("AnimEntreeBtns", (deuxPhases) ? dureeAnimSortieBtns : 0);
    }

    /**
     * fin du délai initié par FragmentBase::actionUlterieure()
     */
    @Override
    protected void callbackActionUlterieure(String action)
    {
        if (action.equals("AnimEntreeBtns")) {
            for (ImageButton btn : btnsSortants) btn.setVisibility(View.GONE);
            for (ImageButton btn : btnsEntrants) btn.setVisibility(View.VISIBLE);
            animAlphaBtns(btnsEntrants, true, dureeAnimEntreeBtns);
        } else if (action.equals("PageRecette")) {
            mainActivity.afficherPage(3);
        } else if (action.equals("PageAccueil")) {
            mainActivity.afficherPage(1);
        }
    }

    /**
     * traitement touche "back"
     * spécifique de ce fragment, la logique par défaut est dans MainActivity
     */
    @Override
    public boolean traiterKeycodeBack()
    {
        if (!sousMenuActif.equals("")) {                // fermeture sous-menu
            configSousMenu("");
        }
        else {                                          // fermeture fragment page
            animAlphaFragment(false, dureeAnimFragment);
            actionUlterieure("PageAccueil", dureeAnimFragment);
        }
        return true;                                    // pas d'action par défaut
    }

    /**
     * event listener clic bouton
     * si bouton sans/avec alcool : ouverture sous-menu
     * si bouton recette : renseigner mainActivity.choixRecette et afficher page 3
     */
    private View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String nomBtn = getResources().getResourceEntryName(view.getId());
            if (estBoutonSousMenu(nomBtn)) {                                     // bouton sous-menu
                configSousMenu(nomBtn);
            } else {                                                             // bouton recette
                mainActivity.choixRecette = nomBtn.replaceAll("^btn[SA]A_", "");
                animAlphaFragment(false, dureeAnimFragment);
                actionUlterieure("PageRecette", dureeAnimFragment);
            }
        }
    };

    /**
     * création de la vue du fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_page2, container, false);
    }

    /**
     * installation des listeners clic boutons
     * et contrôle visibilité boutons
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        for (int iBtn = 0; iBtn < nomsBtns.length; iBtn++) {
            String nomBtn = nomsBtns[iBtn];
            int idBtn = this.getResources().getIdentifier(nomBtn, "id", mainActivity.getPackageName());
            ImageButton btn = view.findViewById(idBtn);
            btn.setOnClickListener(btnOnClickListener);
            btn.setVisibility(View.GONE);
        }
        configSousMenu(sousMenuActif);
        animAlphaFragment(true, dureeAnimFragment);
    }
}