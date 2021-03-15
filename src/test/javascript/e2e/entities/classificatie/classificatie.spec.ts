import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClassificatieComponentsPage, ClassificatieDeleteDialog, ClassificatieUpdatePage } from './classificatie.page-object';

const expect = chai.expect;

describe('Classificatie e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let classificatieComponentsPage: ClassificatieComponentsPage;
  let classificatieUpdatePage: ClassificatieUpdatePage;
  let classificatieDeleteDialog: ClassificatieDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Classificaties', async () => {
    await navBarPage.goToEntity('classificatie');
    classificatieComponentsPage = new ClassificatieComponentsPage();
    await browser.wait(ec.visibilityOf(classificatieComponentsPage.title), 5000);
    expect(await classificatieComponentsPage.getTitle()).to.eq('jhipsterWlsApp.classificatie.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(classificatieComponentsPage.entities), ec.visibilityOf(classificatieComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Classificatie page', async () => {
    await classificatieComponentsPage.clickOnCreateButton();
    classificatieUpdatePage = new ClassificatieUpdatePage();
    expect(await classificatieUpdatePage.getPageTitle()).to.eq('jhipsterWlsApp.classificatie.home.createOrEditLabel');
    await classificatieUpdatePage.cancel();
  });

  it('should create and save Classificaties', async () => {
    const nbButtonsBeforeCreate = await classificatieComponentsPage.countDeleteButtons();

    await classificatieComponentsPage.clickOnCreateButton();

    await promise.all([
      classificatieUpdatePage.setClassificatieInput('classificatie'),
      classificatieUpdatePage.setKleurInput('kleur'),
      classificatieUpdatePage.setOmschrijvingInput('omschrijving'),
    ]);

    expect(await classificatieUpdatePage.getClassificatieInput()).to.eq(
      'classificatie',
      'Expected Classificatie value to be equals to classificatie'
    );
    expect(await classificatieUpdatePage.getKleurInput()).to.eq('kleur', 'Expected Kleur value to be equals to kleur');
    expect(await classificatieUpdatePage.getOmschrijvingInput()).to.eq(
      'omschrijving',
      'Expected Omschrijving value to be equals to omschrijving'
    );

    await classificatieUpdatePage.save();
    expect(await classificatieUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await classificatieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Classificatie', async () => {
    const nbButtonsBeforeDelete = await classificatieComponentsPage.countDeleteButtons();
    await classificatieComponentsPage.clickOnLastDeleteButton();

    classificatieDeleteDialog = new ClassificatieDeleteDialog();
    expect(await classificatieDeleteDialog.getDialogTitle()).to.eq('jhipsterWlsApp.classificatie.delete.question');
    await classificatieDeleteDialog.clickOnConfirmButton();

    expect(await classificatieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
