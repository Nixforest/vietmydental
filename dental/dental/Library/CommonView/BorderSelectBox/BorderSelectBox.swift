//
//  BorderSelectBox.swift
//  PINO
//
//  Created by Lâm Phạm on 7/24/18.
//  Copyright © 2018 lampham. All rights reserved.
//

import UIKit
import harpyframework

enum SelectBoxType {
    case normal
    case picker
    case datePicker
}

@objc protocol BorderSelectBoxDelegate: class {
    func borderSelectBoxDidTouch(box: BorderSelectBox)
    @objc optional func borderSelectBoxDidTouchDidFinishPickDate(box: BorderSelectBox)
}

class BorderSelectBox: BaseView {

    @IBOutlet weak var tfDesc: UITextField!
    @IBOutlet weak var viewBorder: UIView!
    @IBOutlet weak var imgIcon: UIImageView!
    
    var delegate: BorderSelectBoxDelegate!
    
    var type: SelectBoxType = SelectBoxType.normal
    var datePicker: MIDateView!
    var date = Date()
    
    override func firstInit() {
        viewBorder.drawRadius(6, color: "EAEAEA".hexColor(), thickness: 0.5)
    }
    
    func setValue(_ value: String) {
        tfDesc.text = value
    }
    
    func set(placeholder: String, img: UIImage, type: SelectBoxType) {
        self.type = type
        tfDesc.placeholder = placeholder
        imgIcon.image = img
        switch type {
        case .datePicker:
            self.setTypeDatePicker()
            break
        case .picker:
            break
        case .normal:
            break
        }
    }
    
    func setTypeDatePicker() {
        datePicker = MIDateView(type: .justDate)
        datePicker.delegate = self
        let fr = CGRect(x: 0, y: 0, width: UIScreen.main.bounds.width, height: 216)
        datePicker.frame = fr
        tfDesc.inputView = datePicker
    }

    @IBAction func tapAction(_ sender: Any) {
        if type == .datePicker {
            tfDesc.becomeFirstResponder()
        }
        delegate.borderSelectBoxDidTouch(box: self)
    }
}

extension BorderSelectBox: MIDateViewDelegate {
    func dateViewDidFinishPick(dateView: MIDateView) {
        self.date = dateView.date
        tfDesc.text = CommonProcess.getDateString(date: date, format: "dd/MM/yyyy")
        tfDesc.resignFirstResponder()
        delegate.borderSelectBoxDidTouchDidFinishPickDate!(box: self)
    }
}














