package fr.avril.cedric.mojitolibre;

import android.content.DialogInterface;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * classe activité principale
 * cadre (vide) des fragments pages
 */
public class MainActivity extends AppCompatActivity {

	public static boolean debugLog = true;
	private static final String TAG = "MainActivity";

	protected Page1Fragment page1Fragment = null;       // page d'accueil
	protected Page2Fragment page2Fragment = null;       // page menu
	protected Page3Fragment page3Fragment = null;       // page recette

	protected int numPageCourante = 0;
	FragmentBase pageCourante = null;

	protected String choixRecette = null;               // choix recette (page 2)

	/**
	 * démarrage: affichage activity_main + fragment_page1
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		afficherPage(1);
	}

	/**
	 * Affichage d'un fragment page par son numéro
	 * <p>
	 * page d'accueil = 1
	 * page menu = 2
	 * page recette = 3
	 */
	public void afficherPage(int numPage) {
		if (debugLog) Log.v(TAG, "afficherPage(" + numPage + ")");

		// choix objet page à afficher
		// NB: instancier si null

		switch (numPage) {
			case 1:
				if (page1Fragment == null) page1Fragment = new Page1Fragment();
				pageCourante = page1Fragment;
				break;
			case 2:
				if (page2Fragment == null) page2Fragment = new Page2Fragment();
				pageCourante = page2Fragment;
				break;
			case 3:
				if (page3Fragment == null) page3Fragment = new Page3Fragment();
				pageCourante = page3Fragment;
				break;
			default:
				return;
		}
		numPageCourante = numPage;

		// remplissage du FrameLayout "fragmentView" de activity_main.xml par le fragment page cible
		// NB mode transactionnel : on peut laisser tomber si ça plante au milieu !

		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.fragmentView, pageCourante);
		fragmentTransaction.commitAllowingStateLoss();
		getSupportFragmentManager().executePendingTransactions();
	}

	/**
	 * event listener keyup (touche "BACK")
	 */
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!pageCourante.traiterKeycodeBack())         // traitement spécifique
			{                                               // ou
				switch (numPageCourante) {                  // action par défaut:
					case 3:
						afficherPage(2);                    // page recette -> retour page menu
						break;
					case 2:
						afficherPage(1);                    // page menu -> retour page accueil
						break;
					case 1:
						finish();
				}
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * boîte de message
	 */
	public void msgBox(String titre, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
		alertDialog.setTitle(titre);
		alertDialog.setMessage(message);
		alertDialog.setButton(
				AlertDialog.BUTTON_NEUTRAL,
				"OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}
		);
		alertDialog.show();
	}

	/**
	 * boîte de saisie d'un nombre entier (=> callback interface ISaisieEntier)
	 *
	 * @param objetCallback ISaisieEntier objet demandeur de la saisie
	 * @param argCallback String paramètre de la callback identifiant de la question
	 */
	public void boxSaisieEntier(String titre, String question, ISaisieEntier objetCallback, String argCallback) {
		AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
		LayoutInflater factory = LayoutInflater.from(this);
		final View dialogBoxView = factory.inflate(R.layout.boite_saisie_nombre_entier, null);
		adb.setView(dialogBoxView);
		adb.setTitle(titre);
		((TextView) dialogBoxView.findViewById(R.id.Question)).setText(question);
		final ISaisieEntier objCbk = objetCallback;
		final String argCbk = argCallback;
		adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String reponse = ((EditText) dialogBoxView.findViewById(R.id.Reponse)).getText().toString();
				objCbk.callbackSaisieEntier(Integer.decode(reponse), argCbk);
			}
		});
		adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				objCbk.callbackSaisieEntier(null, argCbk);
			}
		});
		adb.show();
	}
	public interface ISaisieEntier {
		void callbackSaisieEntier(@Nullable Integer entierSaisi, String argCallback);
	}
}
