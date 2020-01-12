package fr.avril.cedric.mojitolibre;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Page3Fragment extends FragmentBase {

	private boolean premierAffichage = true;
	ArrayList<Recette> listeRecettes = new ArrayList<Recette>(0);

	// constructeur fragment

	public Page3Fragment()
	{
		Recette recette;
		recette = new Recette("classic", "Mojito Classic", true,
						"\n Déposez les feuilles de menthe fraîche dans le verre. \n \n" +
						"Pour chaque verre, coupez un demi citron vert pour en faire 3 quartiers que vous déposerez directement dedans. \n \n"  +
						"Ajoutez le sucre de canne. \n \n" +
						"A l'aide d'un pilon écrasez les morceaux de citron, afin de libérer le jus. On ne tourne pas le pilon, on appuie juste dessus. \n \n" +
						"Remplissez le verre de glace pilée (jusqu'en haut), ajoutez le rhum. \n \n" +
						"Complétez avec de l'eau gazeuse.\n \n  \n" +
						"L' ASTUCE DE LAURA POUR LA MENTHE: \n \n"  +
						"Effeuillez la menthe sans déchirez les feuilles afin d'avoir un cocktail bien parfumé, sans petits bouts de menthe qui pourraient obstruer les pailles et vous gênez pendant votre dégustation.");
		recette.ajouterIngredient("menthe", 5, "feuilles", "");
		recette.ajouterIngredient("citron vert", 0.5f, "", "");
		recette.ajouterIngredient("sucre de canne", 2f, "cuillères à café", "");
		recette.ajouterIngredient("rhum", 40, "ml", "");
		recette.ajouterIngredient("glace pilée", 0, "", "à volonté");
		recette.ajouterIngredient("eau gazeuse", 0f, "", "compléter");
		listeRecettes.add(recette);
		recette = new Recette("royal", "Mojito Royal", true,
						"\n Déposez les feuilles de menthe fraîche dans le verre. \n \n" +
						"Pour chaque verre, coupez un demi citron vert pour en faire 3 quartiers que vous déposerez directement dedans. \n \n"+
						"Ajoutez le sucre de canne. \n \n" +
						"A l'aide d'un pilon écrasez les morceaux de citron, afin de libérer le jus. On ne tourne pas le pilon, on appuie juste dessus. \n \n"  +
						"Ajoutez de la glace pilée,versez le rhum, mélangez puis complétez avec le champagne.\n \n \n" +
						"L' ASTUCE DE LAURA POUR LE RHUM: \n \n" +
						"Préférez un rhum ambré pour réaliser votre recette");
		recette.ajouterIngredient("menthe", 5, "feuilles", "");
		recette.ajouterIngredient("citron vert", 0.5f, "", "");
		recette.ajouterIngredient("sucre de canne", 2f, "cuillères à café", "");
		recette.ajouterIngredient("rhum", 40, "ml", "");
		recette.ajouterIngredient("glace pilée", 0, "", "à volonté");
		recette.ajouterIngredient("champagne", 0f, "", "compléter");
		listeRecettes.add(recette);
		recette = new Recette("lychee", "Mojito Lychee", false,
						"\n Déposez les feuilles de menthe fraîche dans le verre. \n" +
						"Pour chaque verre, coupez un demi citron vert pour en faire 3 quartiers que vous déposerez directement dedans. \n \n"+
						" A l'aide d'un pilon écrasez les morceaux de citron, afin de libérer le jus. On ne tourne pas le pilon, on appuie juste dessus. \n \n" +
						" Ajoutez le sirop de litchi et remplissez le verre de glace pilée (jusqu'en haut) et complétez avec l'eau gazeuse. \n \n \n" +
						" L' ASTUCE DE LAURA POUR LA DECO: \n \n" +
						" Servez avec des morceaux de litchi dans le verre et dégustez! \n \n");
		recette.ajouterIngredient("menthe", 5, "feuilles", "");
		recette.ajouterIngredient("citron vert", 0.5f, "", "");
		recette.ajouterIngredient("sirop de litchi", 0, "", "un trait");
		recette.ajouterIngredient("glace pilée", 0, "", "à volonté");
		recette.ajouterIngredient("eau gazeuse", 0f, "", "compléter");
		listeRecettes.add(recette);
		recette = new Recette("virgin", "Virgin Mojito", false,
						"\n Déposez les feuilles de menthe fraîche dans le verre. \n \n"  +
						"Pour chaque verre, coupez un demi citron vert pour en faire 3 quartiers que vous déposerez directement dedans. \n \n" +
						"Ajoutez le sucre de canne. \n \n" +
						"A l'aide d'un pilon écrasez les morceaux de citron, afin de libérer le jus. On ne tourne pas le pilon, on appuie juste dessus. \n \n" +
						"Ajoutez le jus de pomme et remplissez le verre de glace pilée (jusqu'en haut) et complétez avec de l'eau gazeuse.\n \n \n" +
						"L' ASTUCE DE LAURA POUR L' EAU GAZEUSE: \n \n" +
						"Privilégiez une eau gazeuse à fines bulles, cela rendra votre mojito meilleur.");
		recette.ajouterIngredient("menthe", 5, "feuilles", "");
		recette.ajouterIngredient("jus de pomme", 50f, "ml", "");
		recette.ajouterIngredient("citron vert", 0.5f, "", "");
		recette.ajouterIngredient("sucre de canne", 2f, "cuillères à café", "");
		recette.ajouterIngredient("eau gazeuse", 0f, "", "compléter");
		recette.ajouterIngredient("glace pilée", 0f, "", "à volonté");
		listeRecettes.add(recette);
	}

	/**
	 * affichage de la recette (+ avertissement alcool au 1er affichage)
	 */
	private void afficherRecette()
	{
		for (int iRecette = 0; iRecette < listeRecettes.size(); iRecette++) {
			Recette recette = listeRecettes.get(iRecette);
			if (recette.code.equals(mainActivity.choixRecette)) {
				TextView tv;
				tv = viewFragment.findViewById(R.id.NombrePersonnes);        // nombre de personnes
				int nombrePersonnes = Integer.decode(tv.getText().toString());
				tv = viewFragment.findViewById(R.id.TitreRecette);           // titre recette
				tv.setText(recette.titre);
				tv = viewFragment.findViewById(R.id.TempsPreparation);       // temps préparation
				tv.setText(Integer.toString(5 + nombrePersonnes));
				tv = viewFragment.findViewById(R.id.IngredientsRecette);     // ingrédients recette
				String ingr = recette.listeIngredients(nombrePersonnes);
				tv.setText(ingr);                                                       // préparation recette
				tv = viewFragment.findViewById(R.id.PreparationRecette);
				tv.setText(recette.preparation);
				if (recette.alcool && premierAffichage)                                 // avertissement alcool
					mainActivity.msgBox(
						"Attention !",
						"L'abus d'alcool est dangereux pour la santé.\nSachez apprécier et consommer avec modération !"
					);
				break;
			}
		}
		premierAffichage = false;
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

	/**
	 * retour saisie nombre entier initiée par MainActivity::boxSaisieEntier()
	 */
	public void callbackSaisieEntier(@Nullable Integer val, String argCallback)
	{
		if (argCallback.equals("retourSaisieNombrePersonnes")) {    // retour saisie nombre personnes
			if (val != null) {
				if (val <= 0) val = 1;
				else if (val > 99) val = 99;
				TextView tv = viewFragment.findViewById(R.id.NombrePersonnes);
				tv.setText(Integer.toString(val));
				afficherRecette();
			}
		}
	}

	/**
     * traitement touche "back"
     * spécifique de ce fragment, la logique par défaut est dans MainActivity
     */
    @Override
    public boolean traiterKeycodeBack()
    {
        animAlphaFragment(false, dureeAnimFragment);
        actionUlterieure("PageMenu", dureeAnimFragment);
        return true;                                        // pas d'action par défaut
    }

    /**
     * création de la vue du fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_page3, container, false);
    }

    /**
     * après création de la vue : affichage texte recette
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
	    super.onViewCreated(view, savedInstanceState);

	    premierAffichage = true;                            // affichage initial recette
	    afficherRecette();

	    TextView tv = viewFragment.findViewById(R.id.NombrePersonnes);
	    tv.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    mainActivity.boxSaisieEntier(               // event listener clic nombre de personnes
		            "Original Mojito",
				    "Pour combien de personnes ?",
				    (FragmentBase) thisFragment,
				    "retourSaisieNombrePersonnes");
		    }
	    });
    }

	// -------------------------------------------------------------------------
	// classe interne "recette"

	private class Recette {
		public String code;
		public String titre;
		public boolean alcool;
		public ArrayList<Ingredient> ingredients;
		public String preparation;

		public Recette(String code, String titre, boolean alcool, String preparation)
		{
			this.code = code;
			this.titre = titre;
			this.alcool = alcool;
			this.ingredients = new ArrayList<Ingredient>(0);
			this.preparation = preparation;
		}

		// ajout d'un ingrédient

		public void ajouterIngredient(String nom, float quantite, String unite, String commentaire)
		{
			ingredients.add( new Ingredient(nom, quantite, unite, commentaire));
		}

		// génération texte liste ingrédients (selon nombre de personnes)

		public String listeIngredients(int nbPersonnes)
		{
			String texte = "";
			for (Ingredient ingredient : ingredients) {
				if (!texte.equals("")) texte += "\n";
				texte += "- " + ingredient.texteIngredient(nbPersonnes);
			}
			return texte;
		}
	}

	// -------------------------------------------------------------------------
	// classe interne "ingrédient"

	private class Ingredient {
		public String nom;
		public float quantite;
		public String unite;
		public String commentaire;

		public Ingredient(String nom, float quantite, String unite, String commentaire)
		{
			this.nom = nom;
			this.quantite = quantite;
			this.unite = unite;
			this.commentaire = commentaire;
		}

		// génération texte ingrédient (selon nombre de personnes)

		public String texteIngredient(int nbPersonnes)
		{
			float quantiteTotale = quantite * nbPersonnes;
			String texte = nom;
			if (quantiteTotale > 0) {
				texte += " : ";
				texte += (quantiteTotale == (long) quantiteTotale)
					   ? String.format("%d", (long)quantiteTotale)
					   : Float.toString(quantiteTotale);
				texte += " " + unite;
			}
			if (!commentaire.equals("")) texte += " (" + commentaire + ")";
			return texte;
		}
	}
}