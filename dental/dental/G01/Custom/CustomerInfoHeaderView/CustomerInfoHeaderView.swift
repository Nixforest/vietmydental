//
//  CustomerInfoHeaderView.swift
//  dental
//
//  Created by Lâm Phạm on 1/14/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

protocol CustomerInfoHeaderViewDelegate: class {
//    func CustomerInfoHeaderViewDidSelect(object: MasterObject)
    func customerInfoHeaderViewDidSelect(object: ConfigExtBean)
}

class CustomerInfoHeaderView: GreenView {

    @IBOutlet weak var lbHeader: UILabel!
    
    var delegate: CustomerInfoHeaderViewDelegate!
//    var object: MasterObject!
    var object: ConfigExtBean!
    
    override func initStyle() {
        self.backgroundColor = UIColor.white
    }
    
//    func setHeader(object: MasterObject) {
//        self.object = object
//        self.lbHeader.text = object.name
//    }
    
    func setHeader(bean: ConfigExtBean) {
        self.object = bean
        self.lbHeader.text = bean.name
        self.lbHeader.font = GlobalConst.BASE_FONT
    }

    @IBAction func seeMoreAction(_ sender: Any) {
        delegate.customerInfoHeaderViewDidSelect(object: self.object)
    }
}
