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

public class InstitutionnelSteps {

    private WebDriver driver;
    private Utilisateur utilisateur;

    @Given("je suis sur la page Campus France pour creer un compte institutionnel")
    public void je_suis_sur_la_page_campus_france_pour_creer_un_compte_institutionnel() throws InterruptedException {

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

        System.out.println("Ouverture de la page Campus France - profil institutionnel");
    }

    @When("je charge les donnees du profil institutionnel depuis le fichier JSON")
    public void je_charge_les_donnees_du_profil_institutionnel_depuis_le_fichier_json() {

        List<Utilisateur> utilisateurs = JsonReader.lireUtilisateurs("Data_Institutionnel.json");

        utilisateur = utilisateurs.get(0);

        System.out.println("Utilisateur institutionnel charge : "
                + utilisateur.prenom + " " + utilisateur.nom);
    }

    @And("je renseigne les informations personnelles du compte institutionnel")
    public void je_renseigne_les_informations_personnelles_du_compte_institutionnel() throws InterruptedException {

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

        System.out.println("Informations personnelles institutionnel remplies");
    }

    @And("je renseigne les informations specifiques au profil institutionnel")
    public void je_renseigne_les_informations_specifiques_au_profil_institutionnel() throws InterruptedException {

        disableAvatarWidget();

        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"edit-field-publics-cibles\"]/div[3]/label")).click();

        System.out.println("Case Institutionnel cochee");

        Thread.sleep(1000);
        driver.findElement(By.id("edit-field-fonction-0-value")).sendKeys(utilisateur.fonction);

        Thread.sleep(1000);
        driver.findElement(By.cssSelector("#edit-field-type-organisme-selectized"))
                .sendKeys(utilisateur.typeOrganisme);

        Thread.sleep(1000);
        driver.findElement(By.cssSelector("#edit-field-type-organisme-selectized"))
                .sendKeys(Keys.ENTER);

        Thread.sleep(1000);
        driver.findElement(By.id("edit-field-nom-organisme-0-value"))
                .sendKeys(utilisateur.nomOrganisme);

        System.out.println("Informations specifiques institutionnel remplies");
    }

    @And("j accepte les conditions pour le compte institutionnel")
    public void j_accepte_les_conditions_pour_le_compte_institutionnel() throws InterruptedException {

        disableAvatarWidget();

        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"edit-field-accepte-communications-wrapper\"]/div/label")).click();

        System.out.println("Conditions acceptees pour institutionnel");
    }

    @Then("le formulaire institutionnel doit etre correctement rempli")
    public void le_formulaire_institutionnel_doit_etre_correctement_rempli() throws InterruptedException {

        System.out.println("Verification du formulaire institutionnel");

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
        """);

        System.out.println("Bloc avatar desactive");
    }
}