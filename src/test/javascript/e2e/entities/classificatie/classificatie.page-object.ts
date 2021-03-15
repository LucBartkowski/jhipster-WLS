import { element, by, ElementFinder } from 'protractor';

export class ClassificatieComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-classificatie div table .btn-danger'));
  title = element.all(by.css('jhi-classificatie div h2#page-heading span')).first();
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

export class ClassificatieUpdatePage {
  pageTitle = element(by.id('jhi-classificatie-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  classificatieInput = element(by.id('field_classificatie'));
  kleurInput = element(by.id('field_kleur'));
  omschrijvingInput = element(by.id('field_omschrijving'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setClassificatieInput(classificatie: string): Promise<void> {
    await this.classificatieInput.sendKeys(classificatie);
  }

  async getClassificatieInput(): Promise<string> {
    return await this.classificatieInput.getAttribute('value');
  }

  async setKleurInput(kleur: string): Promise<void> {
    await this.kleurInput.sendKeys(kleur);
  }

  async getKleurInput(): Promise<string> {
    return await this.kleurInput.getAttribute('value');
  }

  async setOmschrijvingInput(omschrijving: string): Promise<void> {
    await this.omschrijvingInput.sendKeys(omschrijving);
  }

  async getOmschrijvingInput(): Promise<string> {
    return await this.omschrijvingInput.getAttribute('value');
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

export class ClassificatieDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-classificatie-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-classificatie'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
