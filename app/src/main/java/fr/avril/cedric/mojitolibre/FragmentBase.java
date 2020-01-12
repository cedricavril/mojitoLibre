package fr.avril.cedric.mojitolibre;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * classe de base fragment
 */
public class FragmentBase extends Fragment implements MainActivity.ISaisieEntier {

    protected MainActivity mainActivity = null;             // activité contenant l'objet fragment
    protected View viewFragment = null;                     // view associée à l'objet fragment
    protected Fragment thisFragment = this;                 // "this" global (cf. onClickListener)

    final int dureeAnimFragment = 300;                      // durée apparition / disparition fragment

    protected static String TAG = "FragmentBase";           // TAG (debug)

    /**
     * constructeur du fragment
     */
    public FragmentBase()
    {
        TAG = this.getClass().getSimpleName();
    }

    /**
     * animation canal alpha du fragment
     */
    protected void animAlphaFragment(boolean apparition, int duree)
    {
        int idLayoutPrincipal = this.getResources().getIdentifier("layoutPrincipal", "id", mainActivity.getPackageName());
        View layoutPrincipal = viewFragment.findViewById(idLayoutPrincipal);
        if (layoutPrincipal != null) {
            float alphaInitial = (apparition) ? 0f : 1f;
            float alphaFinal = 1 - alphaInitial;
            AlphaAnimation anim = new AlphaAnimation(alphaInitial, alphaFinal);
            anim.setDuration(duree);
            layoutPrincipal.startAnimation(anim);
        }
    }

    /**
     * setTimer
     */
    protected void actionUlterieure(String action, int delai)
    {
        (new StaticHandler()).postDelayed(new StaticRunnableDelai(this, action), delai);
    }

    /**
     * fin du délai initié par FragmentBase::actionUlterieure()
     */
    protected void callbackActionUlterieure(String action)
    {
    }

	/**
	 * retour saisie nombre entier initiée par MainActivity::boxSaisieEntier()
	 */
    public void callbackSaisieEntier(@Nullable Integer val, String argCallback)
    {
    }

    /* exécution temporisée : basiquement, handler.postDelayed(new Runnable() { ... }
     * Le handler traite directement avec la file d'attente de messages du thread courant.
     * Un runnable non statique garde une référence implicite sur l'instance du Fragment et
     * cela la rend inéligible au GC => il faut un runnable classe interne statique et lui donner
     * une référence faible sur le contexte.
     * Pourquoi aussi un handler statique ? Le runnable est créé avant le postDelayed() et
     * embarque directement une référence au contexte. En quoi cela concerne-t-il le handler ?
     */
    protected static class StaticHandler extends Handler                    // classe interne statique handler
    {
    }

    protected static class StaticRunnableDelai implements Runnable          // classe interne statique runnable
    {
        private final WeakReference<FragmentBase> weakRefFragmentBase;
        String action;

        public StaticRunnableDelai(FragmentBase contexte, String action) {  // constructeur
            weakRefFragmentBase = new WeakReference<FragmentBase>(contexte);
            this.action = action;
        }

        @Override
        public void run() {                                                 // action sous timer
            FragmentBase fragmentBase = weakRefFragmentBase.get();
            if (fragmentBase != null) fragmentBase.callbackActionUlterieure(action);
        }
    }

    /**
     * traitement touche "back"
     * True: traité
     * False: non traité, action par défaut de l'activité
     */
    public boolean traiterKeycodeBack()
    {
        return false;
    }

    /**
     * après création vue
     * stockage référence vue
     * configuration image id="ImageBack" en faux bouton "back" => OnClickListener()
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        viewFragment = view;
        super.onViewCreated(view, savedInstanceState);
        animAlphaFragment(true, dureeAnimFragment);

	    int idImageBack = this.getResources().getIdentifier("ImageBack", "id", mainActivity.getPackageName());
	    ImageView iv = view.findViewById(idImageBack);
	    if (iv != null) {
		    iv.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View view) {
				    traiterKeycodeBack();
			    }
		    });
	    }
    }

    /**
     * attachement du fragment à son activité
     * NB: on stocke la référence de l'activité pour pouvoir lui envoyer des messages
     */
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try {
            mainActivity = (MainActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must be an instance of MainActivity");
        }
    }
}
