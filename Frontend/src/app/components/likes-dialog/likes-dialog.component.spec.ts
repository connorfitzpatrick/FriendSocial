import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LikesDialogComponent } from './likes-dialog.component';

describe('LikesDialogComponent', () => {
  let component: LikesDialogComponent;
  let fixture: ComponentFixture<LikesDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LikesDialogComponent]
    });
    fixture = TestBed.createComponent(LikesDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
