/*
ProbUI - a probabilistic reinterpretation of bounding boxes
designed to facilitate creating dynamic and adaptive mobile touch GUIs.
Copyright (C) 2017  Daniel Buschek

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package de.lmu.ifi.medien.probuidemos.gui.elements;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import de.lmu.ifi.medien.probui.gui.ProbInteractor;
import de.lmu.ifi.medien.probui.gui.ProbInteractorCore;
import de.lmu.ifi.medien.probui.gui.base.ProbUIButton;
import de.lmu.ifi.medien.probui.observations.ProbObservation;
import de.lmu.ifi.medien.probui.observations.ProbObservationTouch;
import de.lmu.ifi.medien.probui.pml.PMLRuleListener;

public class ProbButton extends Button implements ProbInteractor {

    /**
     * Core that manages the probabilistic behaviour of this interactor.
     */
    private ProbInteractorCore core;

    public ProbButton(Context context) {

        super(context);
        sharedConstructor();
    }

    public ProbButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructor();
    }

    public ProbButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sharedConstructor();
    }

    /*
    public ProbButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        sharedConstructor();
    }
    */


    private void sharedConstructor() {
        this.core = new ProbInteractorCore(this);
        this.core.init();

        this.core.debugDrawOutline = false;
        this.core.debugDraw = true;
    }

    @Override
    public ProbInteractorCore getCore() {
        return this.core;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void drawSpecific(Canvas canvas) {
    }

    @Override
    public void onTouchDown(ProbObservationTouch obs) {
    }

    @Override
    public void onTouchMove(ProbObservationTouch obs) {
    }

    @Override
    public void onTouchUp(ProbObservationTouch obs, int numRemainingPointers) {

        if(this.core.isCandidate() && numRemainingPointers==0)
            core.selfExclude(0);
    }

    @Override
    public void onTouchDownPost(ProbObservationTouch obs) {

    }

    @Override
    public void onTouchMovePost(ProbObservationTouch obs) {

    }

    @Override
    public void onTouchUpPost(ProbObservationTouch obs, int numRemainingPointers) {

    }

    @Override
    public String[] getDefaultBehaviours() {
        return null;
    }

    @Override
    public void onCoreObserve(ProbObservation obs) {

    }

    @Override
    public void onCoreFinaliseBehaviourSetup() {
    }


    @Override
    public void onProbSetup() {

        this.core.addBehaviour("tap: Cd*u");
        this.core.addRule("activate: tap on complete and tap is most_likely", new PMLRuleListener() {
            @Override
            public void onRuleSatisfied(String event, int subsequentCalls) {
                core.claimDetermination();
            }
        });
    }

    @Override
    public void onExclude() {
    }

    @Override
    public void onSelfExclude() {

    }

    @Override
    public void onDetermined() {
        this.getView().performClick();
        this.core.undetermine(); // Since a button is undetermined immediately after it is released.
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.core.drawBody(canvas);
    }

}
