//
//  G01F03S04ViewController.swift
//  dental
//
//  Created by Lâm Phạm on 3/15/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class G01F03S04ViewController: ChildExtViewController {
    
    @IBOutlet weak var tvNote: UITextView!
    @IBOutlet weak var btnOk: UIButton!
    @IBOutlet weak var tfDiscount: UITextField!
    @IBOutlet weak var lbAmount: UILabel!
    
    var amount: String = ""
    var detailID: String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        btnOk.drawRadius(4, color: GlobalConst.MAIN_COLOR_GAS_24H, thickness: 1)
        tvNote.drawRadius(4, color: UIColor.lightGray, thickness: 0.5)
        tfDiscount.delegate = self
        tfDiscount.keyboardType = .numberPad;
        tfDiscount.addTarget(self, action: #selector(textFieldChangeAmountValue(sender:)), for: .editingChanged)
        lbAmount.text = amount
        
        let tap = UITapGestureRecognizer(target: self, action: #selector(hideKeyBoard))
        self.view.addGestureRecognizer(tap)
    }
    
    func hideKeyBoard() {
        self.view.endEditing(true)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    //MARK: - Services
    func createReceipt() {
        let req = CreateReceipt_Request()
        req.detail_id = self.detailID
        req.discount = tfDiscount.text!
        req.note = tvNote.text
        req.date = CommonProcess.getDateString(date: Date(), format: "yyyy/MM/dd")
        
        serviceInstance.createReceipt(req: req, success: { (result) in
            self.backButtonTapped(self)
        }) { (error) in
            self.showAlert(message: error.message)
        }
    }
    
    @IBAction func tbnOkAction(_ sender: Any) {
        createReceipt()
    }
    
}

extension G01F03S04ViewController: UITextFieldDelegate {
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let nonNumberSet = NSCharacterSet(charactersIn: "0123456789.").inverted
//        if (string.length == 0) && (range.length > 0) {
//            textField.text = textField.text?.replacingCharacters(in: range as! RangeExpression, with: string)
//            return true
//        }
        if string.trimmingCharacters(in: nonNumberSet as CharacterSet).length > 0 {
            if (textField.text?.length)! >= 100 && (range.length == 0) {
                return false
            } else {
                return true
            }
        }
        return false
    }
    func textFieldChangeAmountValue(sender: UITextField) {
        var strValue = sender.text?.replacingOccurrences(of: ".", with: "")
        strValue = strValue?.replacingOccurrences(of: ",", with: "")
        let strDecimal = CommonProcess.convertStringDecimal(stringValue: strValue!)
        sender.text = strDecimal
    }
}





