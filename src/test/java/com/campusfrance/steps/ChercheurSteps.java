package com.campusfrance.steps;
import org.openqa.selenium.chrome.ChromeOptions;
import com.campusfrance.models.Utilisateur;
import com.campusfrance.utils.JsonReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class ChercheurSteps {

    private WebDriver driver;
    private Utilisateur utilisateur;

    @Given("je suis sur la page Campus France pour creer un compte chercheur")
    public void je_suis_sur_la_page_campus_france_pour_creer_un_compte_chercheur() throws InterruptedException {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        if (System.getenv("GITHUB_ACTIONS") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://www.campusfrance.org/fr/user/register");

        Thread.sleep(4000);

        try {
            WebElement boutonCookies = driver.findElement(By.id("tarteaucitronAllAllowed"));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", boutonCookies);

            System.out.println("Cookies acceptes");
        } catch (Exception e) {
            System.out.println("Popup cookies non trouvee ou deja fermee");
        }

        disableAvatarWidget();

        System.out.println("Ouverture de la page Campus France - profil chercheur");
    }

    @When("je charge les donnees du profil chercheur depuis le fichier JSON")
    public void je_charge_les_donnees_du_profil_chercheur_depuis_le_fichier_json() {

        List<Utilisateur> utilisateurs = JsonReader.lireUtilisateurs("Data_Chercheurs.json");

        utilisateur = utilisateurs.get(0);

        System.out.println("Utilisateur chercheur charge : "
                + utilisateur.prenom + " " + utilisateur.nom);
    }

    @And("je renseigne les informations personnelles du compte chercheur")
    public void je_renseigne_les_informations_personnelles_du_compte_chercheur() throws InterruptedException {

        disableAvatarWidget();


        Thread.sleep(1000);
        driver.findElement(By.name("pass[pass1]")).sendKeys(utilisateur.motDePasse);

        Thread.sleep(1000);
        driver.findElement(By.name("pass[pass2]")).sendKeys(utilisateur.confirmationMotDePasse);

        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"edit-field-civilite\"]/div[2]/label")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("edit-field-nom-0-value")).sendKeys(utilisateur.nom);

        Thread.sleep(1000);
        driver.findElement(By.id("edit-field-prenom-0-value")).sendKeys(utilisateur.prenom);

        Thread.sleep(1000);

        WebElement paysResidence = driver.findElement(By.cssSelector("#edit-field-pays-concernes-selectized"));

        paysResidence.click();

        Thread.sleep(500);
        paysResidence.sendKeys(Keys.BACK_SPACE);

        Thread.sleep(500);
        paysResidence.sendKeys(utilisateur.paysResidence);

        Thread.sleep(1000);
        paysResidence.sendKeys(Keys.ENTER);

        disableAvatarWidget();

        Thread.sleep(1000);
        driver.findElement(By.cssSelector("[id^='edit-field-nationalite-0-target-id']"))
                .sendKeys(utilisateur.paysNationalite);

        Thread.sleep(1000);
        driver.findElement(By.cssSelector("[id^='edit-field-nationalite-0-target-id']"))
                .sendKeys(Keys.ENTER);

        Thread.sleep(1000);
        driver.findElement(By.id("edit-field-code-postal-0-value")).sendKeys(utilisateur.codePostal);

        Thread.sleep(1000);
        driver.findElement(By.id("edit-field-ville-0-value")).sendKeys(utilisateur.ville);

        Thread.sleep(1000);
        driver.findElement(By.id("edit-field-telephone-0-value")).sendKeys(utilisateur.telephone);

        System.out.println("Informations personnelles chercheur remplies");
    }

    @And("je renseigne les informations specifiques au profil chercheur")
    public void je_renseigne_les_informations_specifiques_au_profil_chercheur() throws InterruptedException {

        disableAvatarWidget();

        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"edit-field-publics-cibles\"]/div[2]/label")).click();

        System.out.println("Case Chercheur cochee");

        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"edit-field-publics-cibles\"]/div[1]/label")).click();

        System.out.println("Case Etudiant cochee");

        Thread.sleep(1000);

// Domaine d'études
        WebElement domaineEtudes = driver.findElement(
                By.cssSelector("input[id*='domaine'][id$='selectized']")
        );

        domaineEtudes.click();

        Thread.sleep(500);
        domaineEtudes.sendKeys(Keys.BACK_SPACE);

        Thread.sleep(500);
        domaineEtudes.sendKeys(utilisateur.domaineEtudes);

        Thread.sleep(1000);
        domaineEtudes.sendKeys(Keys.ENTER);

        System.out.println("Domaine d'etudes renseigne : " + utilisateur.domaineEtudes);


// Niveau d'études
        Thread.sleep(1000);

        WebElement niveauEtudes = driver.findElement(
                By.cssSelector("input[id*='niveau'][id$='selectized']")
        );

        niveauEtudes.click();

        Thread.sleep(500);
        niveauEtudes.sendKeys(Keys.BACK_SPACE);

        Thread.sleep(500);
        niveauEtudes.sendKeys(utilisateur.niveauEtudes);

        Thread.sleep(1000);
        niveauEtudes.sendKeys(Keys.ENTER);

        System.out.println("Niveau d'etudes renseigne : " + utilisateur.niveauEtudes);
    }

    @And("j accepte les conditions pour le compte chercheur")
    public void j_accepte_les_conditions_pour_le_compte_chercheur() throws InterruptedException {

        disableAvatarWidget();

        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"edit-field-accepte-communications-wrapper\"]/div/label")).click();

        System.out.println("Conditions acceptees pour chercheur");
    }

    @Then("le formulaire chercheur doit etre correctement rempli")
    public void le_formulaire_chercheur_doit_etre_correctement_rempli() throws InterruptedException {

        System.out.println("Verification du formulaire chercheur");

        Thread.sleep(5000);

    }

    public void disableAvatarWidget() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("""
        const wrapper = document.getElementById('edit-user-picture-wrapper');
        if (wrapper) wrapper.remove();

        const modalButton = document.getElementById('edit-user-picture-entity-browser-entity-browser-open-modal');
        if (modalButton) modalButton.remove();

        const hiddenTarget = document.getElementById('edit-user-picture-target-id');
        if (hiddenTarget) hiddenTarget.value = '';

        const dialogs = document.querySelectorAll('.ui-dialog, .ui-widget-overlay');
        dialogs.forEach(dialog => dialog.remove());

        const tarteaucitronManager = document.getElementById('tarteaucitronManager');
        if (tarteaucitronManager) tarteaucitronManager.remove();

        const tarteaucitronRoot = document.getElementById('tarteaucitronRoot');
        if (tarteaucitronRoot) tarteaucitronRoot.remove();

        const tarteaucitronAlertBig = document.getElementById('tarteaucitronAlertBig');
        if (tarteaucitronAlertBig) tarteaucitronAlertBig.remove();

        const tarteaucitronPercentage = document.getElementById('tarteaucitronPercentage');
        if (tarteaucitronPercentage) tarteaucitronPercentage.remove();

        const cookieElements = document.querySelectorAll('[id^="tarteaucitron"], [class*="tarteaucitron"]');
        cookieElements.forEach(element => element.remove());
    """);

        System.out.println("Bloc avatar et cookies desactives");
    }
}