import { FormGroup, ValidationErrors, ValidatorFn, AbstractControl } from '@angular/forms';

export function ConfirmPasswordValidator(controlName: string, matchingControlName: string): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const passwordControl = control.get(controlName);
    const confirmPasswordControl = control.get(matchingControlName);

    if (passwordControl && confirmPasswordControl && passwordControl.value !== confirmPasswordControl.value) {
      confirmPasswordControl.setErrors({ 'confirmPasswordValidator': true });
      return { 'confirmPasswordValidator': true };
    } else {
      confirmPasswordControl!.setErrors(null);
      return null;
    }
  };
}

