import { element, by, ElementFinder } from 'protractor';

export class GeregistreerdeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-geregistreerde div table .btn-danger'));
  title = element.all(by.css('jhi-geregistreerde div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class GeregistreerdeUpdatePage {
  pageTitle = element(by.id('jhi-geregistreerde-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  voornamenInput = element(by.id('field_voornamen'));
  achternaamInput = element(by.id('field_achternaam'));
  geboortedatumInput = element(by.id('field_geboortedatum'));
  geboorteplaatsInput = element(by.id('field_geboorteplaats'));
  registerNummerInput = element(by.id('field_registerNummer'));
  classificatieInput = element(by.id('field_classificatie'));
  personeelnummerInput = element(by.id('field_personeelnummer'));
  mailadresInput = element(by.id('field_mailadres'));
  telefoonNummerInput = element(by.id('field_telefoonNummer'));
  mobieleNummerInput = element(by.id('field_mobieleNummer'));
  verantwoordelijkeCrestInput = element(by.id('field_verantwoordelijkeCrest'));
  naamInput = element(by.id('field_naam'));

  verantwoordelijkeCrestSelect = element(by.id('field_verantwoordelijkeCrest'));
  naamSelect = element(by.id('field_naam'));
  classificatieSelect = element(by.id('field_classificatie'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setVoornamenInput(voornamen: string): Promise<void> {
    await this.voornamenInput.sendKeys(voornamen);
  }

  async getVoornamenInput(): Promise<string> {
    return await this.voornamenInput.getAttribute('value');
  }

  async setAchternaamInput(achternaam: string): Promise<void> {
    await this.achternaamInput.sendKeys(achternaam);
  }

  async getAchternaamInput(): Promise<string> {
    return await this.achternaamInput.getAttribute('value');
  }

  async setGeboortedatumInput(geboortedatum: string): Promise<void> {
    await this.geboortedatumInput.sendKeys(geboortedatum);
  }

  async getGeboortedatumInput(): Promise<string> {
    return await this.geboortedatumInput.getAttribute('value');
  }

  async setGeboorteplaatsInput(geboorteplaats: string): Promise<void> {
    await this.geboorteplaatsInput.sendKeys(geboorteplaats);
  }

  async getGeboorteplaatsInput(): Promise<string> {
    return await this.geboorteplaatsInput.getAttribute('value');
  }

  async setRegisterNummerInput(registerNummer: string): Promise<void> {
    await this.registerNummerInput.sendKeys(registerNummer);
  }

  async getRegisterNummerInput(): Promise<string> {
    return await this.registerNummerInput.getAttribute('value');
  }

  async setClassificatieInput(classificatie: string): Promise<void> {
    await this.classificatieInput.sendKeys(classificatie);
  }

  async getClassificatieInput(): Promise<string> {
    return await this.classificatieInput.getAttribute('value');
  }

  async setPersoneelnummerInput(personeelnummer: string): Promise<void> {
    await this.personeelnummerInput.sendKeys(personeelnummer);
  }

  async getPersoneelnummerInput(): Promise<string> {
    return await this.personeelnummerInput.getAttribute('value');
  }

  async setMailadresInput(mailadres: string): Promise<void> {
    await this.mailadresInput.sendKeys(mailadres);
  }

  async getMailadresInput(): Promise<string> {
    return await this.mailadresInput.getAttribute('value');
  }

  async setTelefoonNummerInput(telefoonNummer: string): Promise<void> {
    await this.telefoonNummerInput.sendKeys(telefoonNummer);
  }

  async getTelefoonNummerInput(): Promise<string> {
    return await this.telefoonNummerInput.getAttribute('value');
  }

  async setMobieleNummerInput(mobieleNummer: string): Promise<void> {
    await this.mobieleNummerInput.sendKeys(mobieleNummer);
  }

  async getMobieleNummerInput(): Promise<string> {
    return await this.mobieleNummerInput.getAttribute('value');
  }

  async setVerantwoordelijkeCrestInput(verantwoordelijkeCrest: string): Promise<void> {
    await this.verantwoordelijkeCrestInput.sendKeys(verantwoordelijkeCrest);
  }

  async getVerantwoordelijkeCrestInput(): Promise<string> {
    return await this.verantwoordelijkeCrestInput.getAttribute('value');
  }

  async setNaamInput(naam: string): Promise<void> {
    await this.naamInput.sendKeys(naam);
  }

  async getNaamInput(): Promise<string> {
    return await this.naamInput.getAttribute('value');
  }

  async verantwoordelijkeCrestSelectLastOption(): Promise<void> {
    await this.verantwoordelijkeCrestSelect.all(by.tagName('option')).last().click();
  }

  async verantwoordelijkeCrestSelectOption(option: string): Promise<void> {
    await this.verantwoordelijkeCrestSelect.sendKeys(option);
  }

  getVerantwoordelijkeCrestSelect(): ElementFinder {
    return this.verantwoordelijkeCrestSelect;
  }

  async getVerantwoordelijkeCrestSelectedOption(): Promise<string> {
    return await this.verantwoordelijkeCrestSelect.element(by.css('option:checked')).getText();
  }

  async naamSelectLastOption(): Promise<void> {
    await this.naamSelect.all(by.tagName('option')).last().click();
  }

  async naamSelectOption(option: string): Promise<void> {
    await this.naamSelect.sendKeys(option);
  }

  getNaamSelect(): ElementFinder {
    return this.naamSelect;
  }

  async getNaamSelectedOption(): Promise<string> {
    return await this.naamSelect.element(by.css('option:checked')).getText();
  }

  async classificatieSelectLastOption(): Promise<void> {
    await this.classificatieSelect.all(by.tagName('option')).last().click();
  }

  async classificatieSelectOption(option: string): Promise<void> {
    await this.classificatieSelect.sendKeys(option);
  }

  getClassificatieSelect(): ElementFinder {
    return this.classificatieSelect;
  }

  async getClassificatieSelectedOption(): Promise<string> {
    return await this.classificatieSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class GeregistreerdeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-geregistreerde-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-geregistreerde'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
