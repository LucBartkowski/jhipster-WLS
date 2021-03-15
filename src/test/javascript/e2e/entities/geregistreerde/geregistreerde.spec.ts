import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GeregistreerdeComponentsPage, GeregistreerdeDeleteDialog, GeregistreerdeUpdatePage } from './geregistreerde.page-object';

const expect = chai.expect;

describe('Geregistreerde e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let geregistreerdeComponentsPage: GeregistreerdeComponentsPage;
  let geregistreerdeUpdatePage: GeregistreerdeUpdatePage;
  let geregistreerdeDeleteDialog: GeregistreerdeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Geregistreerdes', async () => {
    await navBarPage.goToEntity('geregistreerde');
    geregistreerdeComponentsPage = new GeregistreerdeComponentsPage();
    await browser.wait(ec.visibilityOf(geregistreerdeComponentsPage.title), 5000);
    expect(await geregistreerdeComponentsPage.getTitle()).to.eq('jhipsterWlsApp.geregistreerde.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(geregistreerdeComponentsPage.entities), ec.visibilityOf(geregistreerdeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Geregistreerde page', async () => {
    await geregistreerdeComponentsPage.clickOnCreateButton();
    geregistreerdeUpdatePage = new GeregistreerdeUpdatePage();
    expect(await geregistreerdeUpdatePage.getPageTitle()).to.eq('jhipsterWlsApp.geregistreerde.home.createOrEditLabel');
    await geregistreerdeUpdatePage.cancel();
  });

  it('should create and save Geregistreerdes', async () => {
    const nbButtonsBeforeCreate = await geregistreerdeComponentsPage.countDeleteButtons();

    await geregistreerdeComponentsPage.clickOnCreateButton();

    await promise.all([
      geregistreerdeUpdatePage.setVoornamenInput('voornamen'),
      geregistreerdeUpdatePage.setAchternaamInput('achternaam'),
      geregistreerdeUpdatePage.setGeboortedatumInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      geregistreerdeUpdatePage.setGeboorteplaatsInput('geboorteplaats'),
      geregistreerdeUpdatePage.setRegisterNummerInput('5'),
      geregistreerdeUpdatePage.setClassificatieInput('classificatie'),
      geregistreerdeUpdatePage.setPersoneelnummerInput('personeelnummer'),
      geregistreerdeUpdatePage.setMailadresInput('mailadres'),
      geregistreerdeUpdatePage.setTelefoonNummerInput('telefoonNummer'),
      geregistreerdeUpdatePage.setMobieleNummerInput('mobieleNummer'),
      geregistreerdeUpdatePage.setVerantwoordelijkeCrestInput('verantwoordelijkeCrest'),
      geregistreerdeUpdatePage.setNaamInput('naam'),
      geregistreerdeUpdatePage.verantwoordelijkeCrestSelectLastOption(),
      geregistreerdeUpdatePage.naamSelectLastOption(),
      geregistreerdeUpdatePage.classificatieSelectLastOption(),
    ]);

    expect(await geregistreerdeUpdatePage.getVoornamenInput()).to.eq('voornamen', 'Expected Voornamen value to be equals to voornamen');
    expect(await geregistreerdeUpdatePage.getAchternaamInput()).to.eq('achternaam', 'Expected Achternaam value to be equals to achternaam');
    expect(await geregistreerdeUpdatePage.getGeboortedatumInput()).to.contain(
      '2001-01-01T02:30',
      'Expected geboortedatum value to be equals to 2000-12-31'
    );
    expect(await geregistreerdeUpdatePage.getGeboorteplaatsInput()).to.eq(
      'geboorteplaats',
      'Expected Geboorteplaats value to be equals to geboorteplaats'
    );
    expect(await geregistreerdeUpdatePage.getRegisterNummerInput()).to.eq('5', 'Expected registerNummer value to be equals to 5');
    expect(await geregistreerdeUpdatePage.getClassificatieInput()).to.eq(
      'classificatie',
      'Expected Classificatie value to be equals to classificatie'
    );
    expect(await geregistreerdeUpdatePage.getPersoneelnummerInput()).to.eq(
      'personeelnummer',
      'Expected Personeelnummer value to be equals to personeelnummer'
    );
    expect(await geregistreerdeUpdatePage.getMailadresInput()).to.eq('mailadres', 'Expected Mailadres value to be equals to mailadres');
    expect(await geregistreerdeUpdatePage.getTelefoonNummerInput()).to.eq(
      'telefoonNummer',
      'Expected TelefoonNummer value to be equals to telefoonNummer'
    );
    expect(await geregistreerdeUpdatePage.getMobieleNummerInput()).to.eq(
      'mobieleNummer',
      'Expected MobieleNummer value to be equals to mobieleNummer'
    );
    expect(await geregistreerdeUpdatePage.getVerantwoordelijkeCrestInput()).to.eq(
      'verantwoordelijkeCrest',
      'Expected VerantwoordelijkeCrest value to be equals to verantwoordelijkeCrest'
    );
    expect(await geregistreerdeUpdatePage.getNaamInput()).to.eq('naam', 'Expected Naam value to be equals to naam');

    await geregistreerdeUpdatePage.save();
    expect(await geregistreerdeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await geregistreerdeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last Geregistreerde', async () => {
    const nbButtonsBeforeDelete = await geregistreerdeComponentsPage.countDeleteButtons();
    await geregistreerdeComponentsPage.clickOnLastDeleteButton();

    geregistreerdeDeleteDialog = new GeregistreerdeDeleteDialog();
    expect(await geregistreerdeDeleteDialog.getDialogTitle()).to.eq('jhipsterWlsApp.geregistreerde.delete.question');
    await geregistreerdeDeleteDialog.clickOnConfirmButton();

    expect(await geregistreerdeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
