import React from 'react'
import {Routes, Route} from 'react-router-dom'

import Home from './pages/Home/Home'
import Advert from './pages/Advert/Advert'

const Paths=()=>(
    <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/adverts/:id" element={<Advert/>} />
    </Routes>
)

export default Paths