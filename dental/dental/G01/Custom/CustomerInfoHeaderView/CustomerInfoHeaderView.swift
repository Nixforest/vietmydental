//
//  CustomerInfoHeaderView.swift
//  dental
//
//  Created by Lâm Phạm on 1/14/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit

protocol CustomerInfoHeaderViewDelegate: class {
    func CustomerInfoHeaderViewDidSelect(object: MasterObject)
}

class CustomerInfoHeaderView: GreenView {

    @IBOutlet weak var lbHeader: UILabel!
    
    var delegate: CustomerInfoHeaderViewDelegate!
    var object: MasterObject!
    
    override func initStyle() {
        self.backgroundColor = UIColor.white
    }
    
    func setHeader(object: MasterObject) {
        self.object = object
        self.lbHeader.text = object.name
    }

    @IBAction func seeMoreAction(_ sender: Any) {
        delegate.CustomerInfoHeaderViewDidSelect(object: self.object)
    }
}
