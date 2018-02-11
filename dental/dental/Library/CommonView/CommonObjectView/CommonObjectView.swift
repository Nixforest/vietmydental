//
//  CommonObjectView.swift
//  dental
//
//  Created by Lâm Phạm on 1/15/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit

class CommonObjectView: GreenView {

    @IBOutlet weak var img: UIImageView!
    @IBOutlet weak var lbTitle: UILabel!
    @IBOutlet weak var lbDescription: UILabel!
    
    override func initStyle() {
        img.drawRound()
        img.image?.withRenderingMode(UIImageRenderingMode.alwaysTemplate)
        img.tintColor = UIColor.red
    }
    
    func loadObject(object: MasterObject) {
        lbTitle.text = object.name
        lbDescription.text = object.desc
    }
    func load(title: String, desc: String) {
        lbTitle.text = title
        lbDescription.text = desc
    }
    func load(title: String, desc: String, imgName: String) {
        lbTitle.text = title
        lbDescription.text = desc
        img.image = UIImage.init(named: imgName)
    }

}
